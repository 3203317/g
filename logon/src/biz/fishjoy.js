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

const http = require('http');

const EventProxy = require('eventproxy');

const utils = require('speedt-utils').utils;
const ajax = require('speedt-utils').ajax;

const redis = require('emag.db').redis;

const fishpondPool = require('emag.model').fishpondPool;

const biz = require('emag.biz');

const uuid = require('node-uuid');

(() => {
  var p1 = new Promise((resolve, reject) => {
    ajax(http.request, {
      host: conf.app.resHost,
      port: 80,
      path: '/assets/fish.trail.json',
      method: 'GET',
    }, null, null).then(html => {
      resolve(JSON.parse(html));
    }).catch(reject);
  });

  var p2 = new Promise((resolve, reject) => {
    ajax(http.request, {
      host: conf.app.resHost,
      port: 80,
      path: '/assets/fish.type.json',
      method: 'GET',
    }, null, null).then(html => {
      resolve(JSON.parse(html));
    }).catch(reject);
  });

  var p3 = new Promise((resolve, reject) => {
    ajax(http.request, {
      host: conf.app.resHost,
      port: 80,
      path: '/assets/fish.fixed.json',
      method: 'GET',
    }, null, null).then(html => {
      resolve(JSON.parse(html));
    }).catch(reject);
  });

  Promise.all([p1, p2, p3]).then(values => {

    var fishTrail = values[0].data;
    var fishType  = values[1].data;
    var fishFixed = values[2].data;

    /**
     * 生成一条新鱼
     */
    function createFish1(){

      var newFish = {
        id: utils.replaceAll(uuid.v1(), '-', ''),
        step: 0
      };

      var r = Math.random();

      for(let i in fishType){

        let t = fishType[i];

        if(r >= t.probability){
          newFish.type        = i - 0;
          newFish.path        = Math.round((fishTrail.length - 1) * Math.random());
          newFish.probability = t.probability;
          newFish.weight      = t.weight;
          newFish.hp          = t.hp;
          newFish.loop        = t.loop;
          newFish.trailLen    = fishTrail[newFish.path].length;
          break;
        }
      }

      return newFish;
    }

    function createFish2(fixed, i){

      var fishes = [];

      for(let f of fishFixed[fixed][1][i]){

        var k = fishFixed[fixed][0][f];

        if(!k) continue;

        let t = fishType[k[0]];

        var newFish = {
          id:          utils.replaceAll(uuid.v1(), '-', ''),
          step:        0,
          type:        k[0],
          path:        k[1],
          probability: t.probability,
          weight:      t.weight,
          hp:          t.hp,
          loop:        t.loop,
          trailLen:    fishTrail[k[1]].length,
        };

        fishes.push(newFish);
      }

      return fishes;
    };

    function init(doc, refresh, scene){
      console.log('[INFO ] fishjoy ready init');

      if(!_.isArray(doc)) return;

      var s = doc[1][1];

      var group_info = {};

      for(let i=0, j=s.length; i<j; i++){
        group_info[s[i]] = s[++i];
      }

      var fishpond = fishpondPool.get(group_info.id);

      // 判断当前鱼池是否已经创建
      if(fishpond){
        return biz.group.readyUsers(group_info.id, function (err, doc){
          if(err){
            console.error('[ERROR] %s', err);
            return fishpondPool.release(fishpond.id);
          }

          // 获取所有鱼并发送给举手的人
          refresh(null, [doc, fishpond.getFishes()]);
        });
      }

      // 如果不在同一台服务器
      if(conf.app.id !== group_info.back_id) return;

      fishpond = fishpondPool.create(group_info);

      function scene1(){
        var i = group_info.free_swim_time;

        (function schedule(){

          var timeout = setTimeout(function(){
            clearTimeout(timeout);

            biz.group.readyUsers(group_info.id, function (err, doc){
              if(err){
                console.error('[ERROR] group readyUsers: %s', err);
                return fishpondPool.release(fishpond.id);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === i){
                scene(null, doc);
                fishpond.clear();
                return scene2();
              }

              i--;

              fishpond.refresh();

              var fish = createFish1();

              fish = fishpond.put(fish);

              if(!fish) return schedule();

              refresh(null, [doc, fish]);

              console.info('[INFO ] scene1: %s::%j', i, fish);

              schedule();
            });

          }, 300);
        }());
      }

      function scene2(){
        var fixed = Math.round((fishFixed.length - 1) * Math.random());
        var i     = 0;
        var j     = fishFixed[fixed][1].length - 1;

        (function schedule(){

          var timeout = setTimeout(function(){
            clearTimeout(timeout);

            biz.group.readyUsers(group_info.id, function (err, doc){
              if(err){
                console.error('[ERROR] group readyUsers: %s', err);
                return fishpondPool.release(fishpond.id);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              if(j === i){
                scene(null, doc);
                fishpond.clear();
                return scene1();
              }

              fishpond.refresh();

              var fishes = createFish2(fixed, i);

              var _fishes = [];

              for(let i of fishes){
                let f = fishpond.put(i);
                if(f) _fishes.push(f);
              }

              i++;

              if(0 === _fishes.length) return schedule();

              refresh(null, [doc, _fishes]);

              console.info('[INFO ] scene2: %s::%j', i, _fishes);

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

  }).catch(function (err){
    console.error('[ERROR] resHost: %s', err);
  });

})();

(() => {
  const numkeys = 4;
  const sha1 = 'fca64117dae0991c8fa1ce0eefbaec8d01208e23';

  exports.shot = function(server_id, channel_id, shot, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, shot.id,
      10, shot.x, shot.y, shot.level, (err, doc) => {
        if(err) return cb(err);
        cb(null, doc);
    });
  };
})();


(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.blast = function(server_id, channel_id, blast, cb){
    console.log(blast)
    console.log('----')
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.switch = function(server_id, channel_id, cb){
    // todo
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.tool = function(server_id, channel_id, cb){
    // todo
  };
})();
