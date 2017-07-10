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
  var self = this;
  opts = opts || {};
  self.id = opts.id;
  self.type = opts.type;
  self.capacity = opts.capacity;
};

var pro = Method.prototype;

pro.push = function(){
  // todo
};

pro.kill = function(){
  // todo
};

pro.release = function(){
  // todo
};

pro.scene = function(){
  // todo
}