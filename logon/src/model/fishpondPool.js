/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var Fishpond = require('./fishpond');

var res = module.exports = {};

var fishponds = {};

var free = [];

res.create = function(opts){

  var s = this.get(opts.id);
  if(s) return s.init(opts);

  var b = free.shift();

  if(b){
    b.init(opts);
    fishponds[b.id] = b;
    return b;
  }

  b = new Fishpond(opts);
  fishponds[b.id] = b;
  return b;
};

res.get = function(id){
  return fishponds[id];
};

res.release = function(id){
  var s = this.get(id);
  if(!s) return;
  free.push(s);
  delete fishponds[id];
};
