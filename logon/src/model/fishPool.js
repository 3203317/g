/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var res = module.exports = {};

var fishes = {};

var free = [];

res.create = function(id){

  var s = this.get(id);
  if(s) return s;

  var b = free.shift();

  if(b){
    b.id = id;
    fishes[id] = b;
    return b;
  }

  b = { id: id };
  fishes[id] = b;
  return b;
};

res.get = function(id){
  return fishes[id];
};

res.release = function(id){
  var s = this.get(id);
  if(!s) return;
  free.push(s);
  delete fishes[id];
};
