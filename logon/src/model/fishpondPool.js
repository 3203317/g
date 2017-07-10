/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var Fishbowl = require('./fishbowl');

var res = module.exports = {};

var fishbowls = {};

res.create = function(opts, cb){
  var s = this.get('id');
  if(!s) return cb('');
  var b = new Fishbowl(opts);
  fishbowls[s.id] = b;
};

res.get = function(id){
  return fishbowls[id];
};

res.remove = function(id){
  var s = fishbowls[id];
  if(null == s) return;
  s.release();
  delete fishbowls[id];
};

res.release = function(){};
