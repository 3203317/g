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

    function init(doc, cb){
      console.log('[INFO ] fishjoy ready init');

      if(!_.isArray(doc)) return;

      var sb = doc[1].split('::');

      // 如果不在同一台服务器
      if(conf.app.id !== sb[2]) return;

      var opts = {
        id: sb[0],
        type: sb[1],
        capacity: sb[3],
        fishType: values[1].data,
        fishTrail: values[0].data,
        fishFixed: values[2].data[0],
      };

      var fishpond = fishpondPool.get(opts.id);
      if(fishpond) return;

      fishpond = fishpondPool.create(opts);

      function scene1(fishpond, callback){
        var i = 9;

        (function schedule(){

          var timeout = setTimeout(function(){
            clearTimeout(timeout);
            i--;

            callback((err, doc) => {
              if(err){
                fishpondPool.release(fishpond.id);
                return cb(err);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              var s = [];
              var fishes = fishpond.refresh();

              s.push(doc)
              s.push(fishes);

              cb(null, s);

              console.log('scene1: %s', fishes.length);

              if(0 === i){
                fishpond.clear();
                return scene2(fishpond, callback);
              }

              schedule();
            });

          }, 300);
        }());
      }

      function scene2(fishpond, callback){
        var i = 0;

        (function schedule(){

          var timeout = setTimeout(function(){
            clearTimeout(timeout);
            i++;

            callback((err, doc) => {
              if(err){
                fishpondPool.release(fishpond.id);
                return cb(err);
              }

              if('invalid_group_id' === doc){
                return fishpondPool.release(fishpond.id);
              }

              if(0 === doc.length){
                return fishpondPool.release(fishpond.id);
              }

              var s = [];
              var fishes = fishpond.getFixed(i);

              s.push(doc)
              s.push(fishes);

              cb(null, s);

              console.log('scene2: %s', fishes.length);

              if(65 === i){
                fishpond.clear();
                return scene1(fishpond, callback);
              }

              schedule();
            });

          }, 300);
        }());
      }

      scene1(fishpond, biz.group.readyUsers.bind(null, fishpond.id));
    }

    const numkeys = 3;
    const sha1 = '70ee764d70d6666f1704a3ba7b6697fa27fbd5b0';

    exports.ready = function(server_id, channel_id, cb1, cb2){

      redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, conf.app.id, (new Date().getTime()), (err, doc) => {
        if(err) return cb1(err);
        cb1(null, doc);
        init(doc, cb2);
      });
    };

  }).catch(function (err){
    console.error('[ERROR] resHost: %s', err);
  });

})();

(() => {
  const numkeys = 4;
  const sha1 = '7e72c37c8543eefa1d8041c0de8d58592d55ca16';

  exports.shot = function(server_id, channel_id, shot, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, shot.id,
      10, shot.x, shot.y, shot.level, (err, doc) => {
        if(err) return cb(err);
        console.log(doc);
    });
  };
})();


(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.blast = function(server_id, channel_id, cb){
    // todo
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
