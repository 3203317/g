/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const _ = require('underscore');

var res = module.exports = {};

var fishes = {};

var free = [];

res.create = function(opts){

  var s = this.get(opts.id);
  if(s) return s;

  var b = free.shift();

  if(b){
    b.init(opts);
    fishes[b.id] = b;
    return b;
  }

  b = new Fishpond(opts);
  fishes[b.id] = b;
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
