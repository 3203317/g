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
};

var pro = Component.prototype;

pro.name = '__monitor__';

pro.start = function(cb){
  process.nextTick(cb);
};

pro.afterStart = function(cb){
  process.nextTick(cb);
};

pro.stop = function(force, cb){
};