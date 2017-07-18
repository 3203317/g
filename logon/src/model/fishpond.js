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
  self._fishes = {};
  self._fishWeight = 0;
  self.init(opts);
};

var pro = Method.prototype;

pro.init = function(opts){
  var self = this;
  self.id = opts.id;
  self.capacity = opts.capacity - 0;
  self.type = opts.type;
  return self;
};

pro.clear = function(){
  var self = this;
  self._fishWeight = 0;
  // self._fishes.splice(0, self._fishes.length);
  for(let i in self._fishes){
    delete self._fishes[i];
  }
};

pro.clearFish = function(fish){
  delete this._fishes[fish.id];
  this._fishWeight -= fish.weight;
};

pro.getFishWeight = function(){
  return this._fishWeight;
};

pro.getFishes = function(){
  return this._fishes;
};

/**
 * 放入一条鱼
 *
 * @params
 * @return
 */
pro.put = function(fish, force){
  var self = this;

  if(!force){
    if(self._fishWeight >= self.capacity) return;
  }

  if(self._fishes[fish.id]) return;
  self._fishes[fish.id] = fish;
  self._fishWeight += fish.weight;
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

  for(let i in self._fishes){

    let fish = self._fishes[i];

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

// pro.getFixed = function(i){
//   var self = this;

//   for(let f of self.fishFixed[1][i]){

//     var k = self.fishFixed[0][f];

//     let t = self.fishType[k[0]];

//     var newFish = {
//       id: utils.replaceAll(uuid.v1(), '-', ''),
//       step: 0,
//       type: k[0],
//       path: k[1],
//       probability: t.probability,
//       weight: t.weight,
//       hp: t.hp
//     };

//     self._fishWeight += newFish.weight;
//     self._fishes.push(newFish);
//   }

//   return self._fishes;
// }

// function getFish(){
//   var self = this;

//   if(self._fishWeight >= self.capacity) return;

//   var newFish = {
//     id: utils.replaceAll(uuid.v1(), '-', ''),
//     step: 0
//   };

//   var r = Math.random();

//   for(let i in self.fishType){

//     let t = self.fishType[i];

//     if(r >= t.probability){
//       newFish.type = i - 0;
//       newFish.path = Math.round((self.fishTrail.length - 1) * Math.random());
//       newFish.probability = t.probability;
//       newFish.weight = t.weight;
//       newFish.hp = t.hp;
//       self._fishWeight += t.weight;
//       break;
//     }
//   }

//   return newFish;
// }


// HitFish:function(msg){

//     //取得子弹id对应的子弹信息
//     var bullet = GetBullet(msg.id);
    
//     for(let f of msg._fishes){
         
//         //取得鱼id对应的鱼配置
//         var fish = GetFish(f);

//         var d =  Distance(   fish.path[fish.step].x, fish.path[fish.step].y, 
//                                 bullet.x,bullet.y) ;

//         //计算子弹位置与鱼当前位置 是否合法
//         if(d < bullet.range ) //鱼坐标和子弹坐标 距离小于当前子弹的爆炸半径
//         {//合法命中
           
//            if(--fish.hp <= 0){      //生命值为0，按概率计算能否打死
//                  //取得玩家信息
//                 var p = GetPlayer(bullet.owner);
//                 var r = Math.random();
//                 if(r < fish.die + (1-fish.die)*p.lucky){   //随机值大于死亡率
                   
//                     //检测玩家盈利率  本局玩家打到鱼赚的金币数 / 本局玩家消耗掉的金币 < 玩家盈利率
//                     // if(  ){            }

//                     //返回打到鱼的结果
//                     return {
//                         player_id:0,
//                         fish_id:0,                            
//                         fish_value:0,
//                     };
//                 }
//            }//else{}//不处理

//         }
//         //else{}//非法命中，不处理
//     }       
// }


// //计算两点间距离
// function distance(x1, y1, x2, y2){
//   var xdiff = x2 - x1;
//   var ydiff = y2 - y1;
//   retrun Math.abs(Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5));
// }