/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventEmitter = require('events'),
      util = require('util');

const utils = require('../util/utils');

module.exports = function(app, opts){
  return new Component(app, opts);
};

/**
 *
 */
var Component = function(app, opts){
  var self = this;
  opts = opts || {};
  self.app = app;
  self.sessions = {};  // sid -> session
};

var pro = Component.prototype;

pro.name = '__session__';

pro.get = function(sid){
  return this.sessions[sid];
};

pro.remove = function(sid){
  var session = this.sessions[sid];
  if(session) delete this.sessions[sid];
  return this;
};

pro.create = function(socket){
  var session = new Session(socket, this);
  this.sessions[session.id] = session;
  return session;
};

pro.bind = function(){
  console.log('bind');
};

/*-----分割线-----*/

var Session = function(socket, service){
  var self = this;
  EventEmitter.call(self);
  self.id = socket.id;
  self.settings = {};

  // private
  self.__socket__ = socket;
  self.__sessionService__ = service;
  console.info('[INFO ] Session is created with session id: %s.', self.id);
}

util.inherits(Session, EventEmitter);

Session.prototype.set = function(key, val){
  this.settings[key] = val;
  return this;
};

Session.prototype.get = function(key){
  return this.settings[key];
};


Session.prototype.send = function(msg){
  this.__socket__.send(msg);
  return this;
};

Session.prototype.closed = function(reason){
  var self = this;
  self.__sessionService__.remove(self.id);
  console.info('[INFO ] Session is closed with session id: %s.', self.id);
};