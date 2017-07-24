/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const cfg = require('emag.cfg');

const _ = require('underscore');

const log4js = require('log4js');
const logger = log4js.getLogger('fishpond');

const fishPool = require('./fishPool');

module.exports = function(opts){
  return new Method(opts);
}

var Method = function(opts){
  var self           = this;
  self._fishes       = {};
  self._fishesWeight = 0;
  self.init(opts);
};

var pro = Method.prototype;

pro.init = function(opts){
  var self      = this;
  self.id       = opts.id;
  self.capacity = opts.capacity - 0;
  self.type     = opts.type;
  self._pause   = 0;
  return self;
};

pro.clearAll = function(){
  var self = this;
  // self._fishes.splice(0, self._fishes.length);

  for(let fish of _.values(self._fishes)){
    self.clearFish(fish);
  }

  if(0 !== self._fishesWeight){
    logger.error('clearAll: %s::%s', self.id, self._fishesWeight);
  }
};

pro.clearFish = function(fish){
  var self = this;
  if(!fish) return;
  self._fishesWeight -= fish.weight;
  fishPool.release(fish.id);
  delete self._fishes[fish.id];
};

pro.getFishesWeight = function(){
  return this._fishesWeight;
};

pro.getFishes = function(){
  return this._fishes;
};

pro.pause = function(time){
  if(time) return (this._pause += time);
  if(0 === this._pause) return false;
  if(0 === (--this._pause)) return 'unfreeze';
  return true;
};

/**
 * 放入一条鱼
 *
 * @params
 * @return
 */
pro.put = function(fish, force){
  var self = this;

  if(!fish) return;

  if(!force){
    if(self._fishesWeight >= self.capacity) return;
  }

  if(self._fishes[fish.id]) return;
  self._fishes[fish.id] = fish;
  self._fishesWeight += fish.weight;
  return fish;
};

/**
 * 让所有鱼游动
 *
 * @params
 * @return
 */
pro.refresh = function(){
  var self = this;

  for(let fish of _.values(self._fishes)){

    if(fish.trailLen === fish.step){
      if(fish.loop){
        fish.step = 0;
      }else{
        self.clearFish(fish);
      }
    }else{
      fish.step++;
    }
  }

  return self._fishes;
}

pro.blast = function(bullet, fishes){
  var self = this;

  var result = [];

  for(let f of fishes){

    var fish = self._fishes[f];

    if(!fish) continue;

    logger.debug('blast 1: %j', fish);

    var bullet_info = cfg.bullet[bullet.level - 1];

    if(!bullet_info) continue;

    logger.debug('blast 2: %j', bullet_info);

    var trail_info = cfg.fishTrail[fish.path];

    if(!trail_info) continue;

    logger.debug('blast 3: %j', trail_info);

    var s = trail_info[fish.step];

    var d = distance(s[0], s[1], bullet.x2, bullet.y2);

    if(d > bullet_info.range) continue;

    logger.debug('blast 4: %s', d);

    var r = Math.random();

    if(!(r < cfg.fishType[fish.type].dead_probability)) continue;

    logger.debug('blast 5: %s', r);

    if(!(--fish.hp < 1)){
      logger.debug('blast 6: %j', fish);
      continue;
    }

    // 根据玩家的幸运值与盈亏比率在进行判断
    // 根据配置表生成特殊物品掉落率

    result.push({
      id:     fish.id,
      type:   fish.type,
      money:  cfg.fishType[fish.type].money * bullet.level,
      tool_1: 0,
      tool_2: 0,
    });

    self.clearFish(fish);
  }

  logger.debug('dead fishes: %j', result);

  return result;
};

/**
 * 计算两点间距离
 */
function distance(x1, y1, x2, y2){
  var xdiff = x2 - x1;
  var ydiff = y2 - y1;
  return Math.abs(Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5));
}
