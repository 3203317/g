/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const uuid = require('node-uuid');
const utils = require('speedt-utils').utils;

module.exports = function(opts){
  return new Method(opts);
}

var Method = function(opts){
  var self = this;
  self.fishes = [];
  self.fishType = opts.fishType;
  self.fishTrail = opts.fishTrail;
  self._fishWeight = 0;
  self.init(opts);
};

var pro = Method.prototype;

pro.init = function(opts){
  var self = this;
  self.id = opts.id;
  self.capacity = opts.capacity;
  self.type = opts.type;
  return self;
};

pro.getFishWeight = function(){
  return this._fishWeight;
};

pro.refresh = function(){
  var self = this;

  var s = getFish.call(self);

  //
  if(s) this.fishes.push(s);

  // console.log(fishes);

  for(let i of this.fishes){

    // loop 还没做，如果为false 要从数组中删除

    if(i.step == 200){
      i.step = 0;
  	}
  	else{
  		i.step++;
  	}
  }

  return this.fishes;
}

function getFish(){
  var self = this;

  if(self.getFishWeight() >= self.capacity) return;

  var newFish = {
    id: utils.replaceAll(uuid.v1(), '-', ''),
    step: 0
  };

  var r = Math.random();

  for(let f of self.fishType){
    if(r <= f.probability){
      newFish.type = f.type;
      newFish.path = Math.round((self.fishTrail.length - 1) * Math.random());
      newFish.probability = f.probability;
      newFish.weight = f.weight;
      newFish.loop = f.loop;
      self._fishWeight += f.weight;
      break;
    }
  }

  return newFish;
}
