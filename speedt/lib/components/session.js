/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var utils = require('../util/utils');

module.exports = function(app, opts){
  return new Component(app, opts);
};

/**
 *
 */
var Component = function(app, opts){
  var self = this;
  opts = opts || {};

  self.sessions = {};
};

var pro = Component.prototype;

pro.name = '__session__';

pro.create = function(serverId, socket) {
  var session = new Session(serverId, socket, this);
  this.sessions[session.id] = session;

  return session;
};