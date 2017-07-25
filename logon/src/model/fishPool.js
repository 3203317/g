/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const _      = require('underscore');

const log4js = require('log4js');
const logger = log4js.getLogger('fishPool');

var res = module.exports = {};

var fishes = {};

var free = [];

res.create = function(id){

  logger.debug('%s::%s', free.length, _.size(fishes));

  var fish = this.get(id);
  if(fish) return;

  var newFish = free.shift();

  if(newFish){
    newFish.id = id;
    fishes[id] = newFish;
    return newFish;
  }

  newFish = { id: id };
  fishes[id] = newFish;
  return newFish;
};

res.get = function(id){
  return fishes[id];
};

res.release = function(id){
  var fish = this.get(id);
  if(!fish) return;
  free.push(fish);
  delete fishes[id];
};
