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
};

var pro = Component.prototype;

pro.name = '__monitor__';

pro.start = function(cb){
  var self = this;
  console.info('[INFO ] Monitor component is start.'.green);
  setImmediate(cb);
};

pro.afterStart = function(cb){
  setImmediate(cb);
};

pro.stop = function(force, cb){
  setImmediate(cb);
};