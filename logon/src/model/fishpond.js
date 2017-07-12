/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = function(opts){
  return new Method(opts);
}

var Method = function(opts){
  this.init(opts);
  this.fishes = [];
};

var pro = Method.prototype;

pro.init = function(opts){
  var self = this;
  opts = opts || {};
  self.id = opts.id;
  self.capacity = opts.capacity;
};