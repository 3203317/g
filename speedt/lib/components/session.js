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

pro.create = function(socket){
  var session = new Session(socket);
  this.sessions[session.id] = session;
  return session;
};

var Session = function(socket){
  var self = this;
  EventEmitter.call(self);
  self.id = socket.id;

  // private
  self.__socket__ = socket;
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

Session.prototype.close = function(reason){
  var self = this;
  setImmediate(self.__socket__.disconnect.bind(null));
};