/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = function(opts){
  return new Method(opts);
}

var Method = function(opts){
  var self = this;
  opts = opts || {};
  self.id = opts.id;
  self.type = opts.type;
  self.weight = opts.weight;
};

var pro = Method.prototype;