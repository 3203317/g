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

const biz = require('emag.biz');

(() => {

  function init(doc, cb){
    console.log('[INFO ] fishjoy ready init');

    if(!_.isArray(doc)) return;

    var sb = doc[1].split('::');

    // 如果不在同一台服务器
    if(conf.app.id !== sb[0]) return;

    var opts = {
      id: sb[1],
      capacity: sb[2],
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
            if(err) return cb(err);

            if(0 === doc.length){
              return fishpondPool.release(fishpond.id);
            }

            var s = [];
            s.push(doc)
            s.push(fishpond.getFishes());

            cb(null, s);

            console.log('scene1: %j', doc);

            if(0 === i) return scene2(fishpond, callback);
            schedule();
          });

        }, 300);
      }());
    }

    function scene2(fishpond, callback){
      var i = 6;

      (function schedule(){

        var timeout = setTimeout(function(){
          clearTimeout(timeout);
          i--;

          callback((err, doc) => {
            if(err) return cb(err);

            if(0 === doc.length){
              return fishpondPool.release(fishpond.id);
            }

            var s = [];
            s.push(doc)
            s.push(fishpond.getFishes());

            cb(null, s);

            console.log('scene2: %j', doc);

            if(0 === i) return scene1(fishpond, callback);
            schedule();
          });

        }, 300);
      }());
    }

    scene1(fishpond, biz.group.users.bind(null, fishpond.id));
  }

  const numkeys = 3;
  const sha1 = '26453f18b0c16646a987191d15cd816b816ebd47';

  exports.ready = function(server_id, channel_id, cb1, cb2){

    redis.evalsha(sha1, numkeys, conf.redis.database, conf.app.id, null, server_id, channel_id, (new Date().getTime()), (err, doc) => {
      if(err) return cb1(err);
      cb1(null, doc);
      init(doc, cb2);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '';

  exports.shot = function(server_id, channel_id, cb){
    // todo
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
