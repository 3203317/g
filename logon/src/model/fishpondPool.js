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

  var b = free[0];

  if(b){
    free.splice(0, 1);
    b.init(opts);
    fishponds[b.id] = b;
    return this.get(b.id);
  }

  b = new Fishpond(opts);
  fishponds[b.id] = b;
  return this.get(b.id);
};

res.get = function(id){
  return fishponds[id];
};

res.release = function(fishpond){
  if(!fishpond) return;
  var s = this.get(fishpond.id);
  if(!s) return;
  free.push(s);
  delete fishponds[fishpond.id];
};
