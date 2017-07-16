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
  self.fishFixed = opts.fishFixed;
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

pro.clear = function(){
  this._fishWeight = 0;
  this.fishes.splice(0, this.fishes.length);
};

pro.getFishWeight = function(){
  return this._fishWeight;
};

pro.refresh = function(){
  var self = this;

  var s = getFish.call(self);

  if(s) self.fishes.push(s);

  for(let i in self.fishes){

    let f = self.fishes[i];

    if((self.fishTrail[f.path].length - 1) === f.step){
      if(self.fishType[f.type].loop){
        f.step = 0;
      }else{
        self._fishWeight -= f.weight;
        self.fishes.splice(i, 1);
      }
    }else{
      f.step++;
    }
  }

  return self.fishes;
}

pro.getFixed = function(i){
  var self = this;

  for(let f of self.fishFixed[1][i]){

    if(0 === f.length) return;

    for(let j of f){

      var k = self.fishFixed[0][j];

      let t = self.fishType[k[0]];

      var newFish = {
        id: utils.replaceAll(uuid.v1(), '-', ''),
        step: 0,
        type: k[0],
        path: k[1],
        probability: t.probability,
        weight: t.weight,
      };

      self._fishWeight += newFish.weight;
      self.fishes.push(newFish);
    }
  }

  return self.fishes;
}

function getFish(){
  var self = this;

  if(self._fishWeight >= self.capacity) return;

  var newFish = {
    id: utils.replaceAll(uuid.v1(), '-', ''),
    step: 0
  };

  var r = Math.random();

  for(let i in self.fishType){

    let t = self.fishType[i];

    if(r >= t.probability){
      newFish.type = i - 0;
      newFish.path = Math.round((self.fishTrail.length - 1) * Math.random());
      newFish.probability = t.probability;
      newFish.weight = t.weight;
      self._fishWeight += t.weight;
      break;
    }
  }

  return newFish;
}
