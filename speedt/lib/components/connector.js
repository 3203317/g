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
  self.app = app;
  self.encode = opts.encode;
  self.decode = opts.decode;
  self.connector = getConnector(app, opts);
};

var pro = Component.prototype;

pro.name = '__connector__';

pro.start = function(cb){
  var self = this;
  console.log('__connector__ start');
  setImmediate(cb);
};

pro.afterStart = function(cb){
  setImmediate(cb);
};

pro.stop = function(force, cb){
  setImmediate(cb);
};

const getConnector = (app, opts) => {
  var connector = opts.connector;
  if(!connector) return getDefaultConnector(app, opts);
  if('function' !== typeof connector) return connector;
  return connector(app.port, app.host, opts);
};

const getDefaultConnector = (app, opts) => {
  var DefaultConnector = require('../connectors/hyxconnector');
  return new DefaultConnector(app.port, app.host, opts);
};