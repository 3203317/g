/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

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

pro.create = function(sid){
  var session = new Session();
  this.sessions[session.id] = session;
  return session;
};