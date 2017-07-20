/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const path = require('path');
const cwd = process.cwd();

const conf = require(path.join(cwd, 'settings'));

const _ = require('underscore');

const EventProxy = require('eventproxy');

const utils = require('speedt-utils').utils;

const redis = require('emag.db').redis;

const fishpondPool = require('emag.model').fishpondPool;
const fishPool     = require('emag.model').fishPool;

const biz = require('emag.biz');
const cfg = require('emag.cfg');

const uuid = require('node-uuid');

const log4js = require('log4js');

// log4js.configure({
//   appenders: {
//     fishjoy: {
//       type: 'dateFile',
//       filename: path.join(cwd, 'logs'),
//       pattern: 'yyyy-MM-dd.log',
//       alwaysIncludePattern: true
//     },
//     console: {
//       type: 'console'
//     }
//   },
//   categories: {
//     default: {
//       appenders: ['fishjoy', 'console'],
//       level: 'debug'
//     }
//   }
// });

const logger = log4js.getLogger('fishjoy');

(() => {

  // function transform(obj){
  //   let arr = [];
  //   for(let item in obj){
  //     arr.push(obj[item]);
  //   }
  //   return arr;
  // }

  /**
   * 从池中生成一条新鱼
   */
  function createFish1(){

    var newFish = fishPool.create(utils.replaceAll(uuid.v1(), '-', ''));

    if(!newFish) return;

    var r = Math.random();

    for(let i in cfg.fishType){

      let t = cfg.fishType[i];

      if(r >= t.probability){
        newFish.step        = 0;
        newFish.type        = i - 0;
        newFish.path        = Math.round((cfg.fishTrail.length - 1) * Math.random());
        newFish.probability = t.probability;
        newFish.weight      = t.weight;
        newFish.hp          = t.hp;
        newFish.loop        = t.loop;
        newFish.trailLen    = cfg.fishTrail[newFish.path].length;
        break;
      }
    }

    return newFish;
  }

  function createFish2(fixed, i){

    var fishes = [];

    for(let f of cfg.fishFixed[fixed][1][i]){

      var k = cfg.fishFixed[fixed][0][f];

      if(!k) continue;

      let t = cfg.fishType[k[0]];

      var newFish = fishPool.create(utils.replaceAll(uuid.v1(), '-', ''));

      if(!newFish) continue;

      newFish.step        = 0;
      newFish.type        = k[0];
      newFish.path        = k[1];
      newFish.probability = t.probability;
      newFish.weight      = t.weight;
      newFish.hp          = t.hp;
      newFish.loop        = t.loop;
      newFish.trailLen    = cfg.fishTrail[newFish.path].length;

      fishes.push(newFish);
    }

    return fishes;
  };

  function init(doc, refresh, scene){
    logger.info('fishjoy ready init');

    if(!_.isArray(doc)) return;

    var group_info = cfg.arrayToObject(doc[1][1]);

    var fishpond = fishpondPool.get(group_info.id);

    // 判断当前鱼池是否已经创建
    if(fishpond){
      return biz.group.readyUsers(group_info.id, function (err, doc){
        if(err){
          logger.error('group readyUsers: %s', err);
          return fishpondPool.release(fishpond.id);
        }

        if(!_.isArray(doc)) return;

        // 获取所有鱼并发送给举手的人
        refresh(null, [doc, _.values(fishpond.getFishes())]);
      });
    }

    // 如果不在同一台服务器
    if(conf.app.id !== group_info.back_id) return;

    fishpond = fishpondPool.create(group_info);

    if(!fishpond) return;

    function scene1(){
      var i = group_info.free_swim_time;

      (function schedule(){

        var timeout = setTimeout(function(){
          clearTimeout(timeout);

          if(0 === i){
            return biz.group.readyUsers(group_info.id, function (err, doc){
              if(err){
                logger.error('group readyUsers: %s', err);
                return fishpondPool.release(fishpond.id);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              scene(null, doc);
              fishpond.clear();
              scene2();
            });
          }

          i--;

          fishpond.refresh();

          var fish = createFish1();

          if(!fish) return schedule();

          fish = fishpond.put(fish);

          if(!fish) return schedule();

          biz.group.readyUsers(group_info.id, function (err, doc){
            if(err){
              logger.error('group readyUsers: %s', err);
              return fishpondPool.release(fishpond.id);
            }

            if('invalid_group_id' === doc){
              return fishpondPool.release(fishpond.id);
            }

            if(0 === doc.length){
              return fishpondPool.release(fishpond.id);
            }

            refresh(null, [doc, [fish]]);

            logger.info('scene1: %s::%j', i, fish);

            schedule();
          });

        }, 300);
      }());
    }

    function scene2(){
      var fixed = Math.round((cfg.fishFixed.length - 1) * Math.random());
      var i     = 0;
      var j     = cfg.fishFixed[fixed][1].length - 1;

      (function schedule(){

        var timeout = setTimeout(function(){
          clearTimeout(timeout);

          if(j === i){
            return biz.group.readyUsers(group_info.id, function (err, doc){
              if(err){
                logger.error('group readyUsers: %s', err);
                return fishpondPool.release(fishpond.id);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              scene(null, doc);
              fishpond.clear();
              scene1();
            });
          }

          // 刷新池
          fishpond.refresh();

          var fishes = createFish2(fixed, i);

          i++;

          for(let m in fishes){
            let n = fishes[m];
            let f = fishpond.put(n, true);
            if(!f) fishes.splice(m, 1);
          }

          if(0 === fishes.length) return schedule();

          biz.group.readyUsers(group_info.id, function (err, doc){
            if(err){
              logger.error('group readyUsers: %s', err);
              return fishpondPool.release(fishpond.id);
            }

            if('invalid_group_id' === doc){
              return fishpondPool.release(fishpond.id);
            }

            if(0 === doc.length){
              return fishpondPool.release(fishpond.id);
            }

            refresh(null, [doc, fishes]);

            logger.info('scene2: %s::%j', i, fishes);

            schedule();
          });

        }, 300);
      }());
    };

    scene2();
  }

  const numkeys = 3;
  const sha1 = '5308f6f6460a36b7bb01319971f6b86a54d12283';

  exports.ready = function(server_id, channel_id, ready, refresh, scene){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, conf.app.id, (new Date().getTime()), (err, doc) => {
      if(err) return ready(err);
      ready(null, doc);
      init(doc, refresh, scene);
    });
  };

})();

(() => {
  const numkeys = 4;
  const sha1 = 'a0b748bd63eb19f4994d3246dd49298bea99e3a6';

  exports.shot = function(server_id, channel_id, shot, cb){

    if(!_.isString(shot.id))    return cb(null, 'invalid_shot');
    if(!_.isNumber(shot.x))     return cb(null, 'invalid_shot');
    if(!_.isNumber(shot.y))     return cb(null, 'invalid_shot');
    if(!_.isNumber(shot.level)) return cb(null, 'invalid_shot');

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, shot.id,
      99, shot.x, shot.y, shot.level, (err, doc) => {
        if(err) return cb(err);
        cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.blast = function(server_id, channel_id, blast, cb){

    if(!_.isArray(blast))  return cb(null, 'invalid_blast');
    if(2 !== blast.length) return cb(null, 'invalid_blast');

    var bullet_blast = blast[0];
    if(!_.isObject(bullet_blast))   return cb(null, 'invalid_blast');
    if(!_.isNumber(bullet_blast.x)) return cb(null, 'invalid_blast');
    if(!_.isNumber(bullet_blast.y)) return cb(null, 'invalid_blast');
    if(!_.isString(bullet_blast.id)) return cb(null, 'invalid_blast');

    var hit_fishes = blast[1];
    if(!_.isArray(hit_fishes)) return cb(null, 'invalid_blast');
    if(0 === blast.length)     return cb(null, 'invalid_blast');

    // 

    // var bb     = blast[0];
    // var fishes = blast[1];

    var self = this;

    self.bullet(server_id, channel_id, bullet_blast.id, function (err, doc){
      if(err) return cb(err);

      if(!_.isArray(doc)) return cb(null, doc);

      // var s = doc[0];
      // var b = doc[1];

      // var user = {};

      // for(let i=0, j=s.length; i<j; i++){
      //   user[s[i]] = s[++i];
      // }


      var user_info = cfg.arrayToObject(doc[0]);

      var fishpond = fishpondPool.get(user_info.group_id);

      // 判断当前鱼池是否存在
      if(!fishpond) return;

      // var bullet = {};

      // for(let i=0, j=b.length; i<j; i++){
      //   bullet[b[i]] = b[++i];
      // }

      // bullet.x = bb.x;
      // bullet.y = bb.y;


      var bullet_info = cfg.arrayToObject(doc[1]);

      bullet_info.x2 = bullet_blast.x;
      bullet_info.y2 = bullet_blast.y;

      // logger.info(user);
      // logger.info(bullet);

      var result = fishpond.blast(bullet_info, hit_fishes);

      // logger.info('-----')
      // logger.info(result);

      if(0 === result.length) return;


      // var ssss = JSON.parse(user.extend_data);
      // result.push(ssss.id);

      result.push(bullet_info.user_id);


      biz.group.readyUsers(user_info.group_id, function (err, doc){
        if(err) return cb(err);
        cb(null, [doc, result]);
      });

    });
  };

})();

(() => {
  const numkeys = 3;
  const sha1 = '21cd39edcb3ef61ef97644323bfb2445da755d4e';

  exports.switch = function(server_id, channel_id, level, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, level, (err, doc) => {
        if(err) return cb(err);
        if(!_.isArray(doc)) return cb(null, doc);
        cb(null, [doc[0], [doc[1], level]]);
    });

  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '';

  function freeze(server_id, channel_id, tool_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, tool_id, (err, doc) => {
        if(err) return cb(err);
        if(!_.isArray(doc)) return cb(null, doc);
        doc[1].push(tool_id);
        cb(null, doc);
    });
  }

  exports.tool = function(server_id, channel_id, tool, cb){

    if(!_.isArray(tool)) return;

    var tool_id = tool[0];

    switch(tool_id){
      case 'freeze': return freeze(server_id, channel_id, 1, cb);
      default: break;
    }

  };
})();

(() => {
  const numkeys = 4;
  const sha1 = 'f0a5eea16f9410ac90c223666f109544887f889d';

  /**
   *
   * fishjoy_bullet.lua
   *
   * @return
   */
  exports.bullet = function(server_id, channel_id, bullet_id, cb){
    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, bullet_id, (err, doc) => {
        if(err) return cb(err);
        cb(null, doc);
    });
  };
})();
