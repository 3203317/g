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

    function init(doc, refresh, scene){
      console.log('[INFO ] fishjoy ready init');

      if(!_.isArray(doc)) return;

      var s = doc[1][1];

      var group_info = {};

      for(let i=0, j=s.length; i<j; i++){
        group_info[s[i]] = s[++i];
      }

      // 如果不在同一台服务器
      if(conf.app.id !== group_info.back_id) return;

      // 判断当前服务器是否已经创建
      var fishpond = fishpondPool.get(group_info.id);
      if(fishpond) return;

      // 

      // var opts = {
      //   id: group_info.id,
      //   type: group_info.type,
      //   capacity: group_info.capacity,
      //   // fishTrail: values[0].data,
      //   // fishType: values[1].data,
      //   // fishFixed: values[2].data[0],
      // };

      group_info.fishTrail = values[0].data;
      group_info.fishType  = values[1].data;

      fishpond = fishpondPool.create(group_info);

      function scene1(){
        var i = group_info.free_swim_time;

        (function schedule(){

          var timeout = setTimeout(function(){
            clearTimeout(timeout);
            i--;

            biz.group.readyUsers(group_info.id, function (err, doc){
              if(err){
                console.error('[ERROR] %s', err);
                return fishpondPool.release(fishpond.id);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              var sb = [];
              sb.push(doc);

              var fishes = fishpond.refresh();
              sb.push(fishes);

              refresh(null, sb);

              console.info('[INFO ] scene1: %s::%s', i, fishes.length);

              if(0 === i){
                scene(null, doc);
                fishpond.clear();
                return scene1();
              }

              schedule();
            });

          }, 300);
        }());
      }

      // function scene2(fishpond, callback){
      //   var i = 0;

      //   (function schedule(){

      //     var timeout = setTimeout(function(){
      //       clearTimeout(timeout);
      //       i++;

      //       callback((err, doc) => {
      //         if(err){
      //           fishpondPool.release(fishpond.id);
      //           return refresh(err);
      //         }

      //         if('invalid_group_id' === doc){
      //           return fishpondPool.release(fishpond.id);
      //         }

      //         if(0 === doc.length){
      //           return fishpondPool.release(fishpond.id);
      //         }

      //         var s = [];
      //         var fishes = fishpond.getFixed(i);

      //         s.push(doc)
      //         s.push(fishes);

      //         refresh(null, s);

      //         console.log('scene2: %s %s', i, fishes.length);

      //         if((opts.fishFixed[1].length - 1) === i){
      //           scene(null, doc);
      //           fishpond.clear();
      //           return scene1(fishpond, callback);
      //         }

      //         schedule();
      //       });

      //     }, 300);
      //   }());
      // }

      scene1(fishpond, biz.group.readyUsers.bind(null, group_info.id));
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
