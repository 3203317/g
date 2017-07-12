/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const path = require('path');
const cwd = process.cwd();

const conf = require(path.join(cwd, 'settings'));

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const db = require('emag.db');
const redis = db.redis;

const fishpondPool = require('emag.model').fishpondPool;

const biz = require('emag.biz');

(() => {

  function init(doc, cb){
    console.log('[INFO ] fishjoy ready init');

    switch(doc){
      case 'invalid_group_id':
      case 'invalid_group_pos_id':
      case 'already_raise_hand':
      case 'invalid_user_id': return;
    }

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

    function scene1(){
      var i = 9;

      (function schedule(){

        var timeout = setTimeout(function(){
          clearTimeout(timeout);
          i--;

          biz.group.users(opts.id, (err, doc) => {
            if(err) return cb(err);

            console.log('scene1: %j', doc);

            if(0 === i) return scene2();
            schedule();
          });

        }, 300);
      }());
    }

    function scene2(){
      var i = 6;

      (function schedule(){

        var timeout = setTimeout(function(){
          clearTimeout(timeout);
          i--;

          biz.group.users(opts.id, (err, doc) => {
            if(err) return cb(err);

            console.log('scene2: %j', doc);

            if(0 === i) return scene1();
            schedule();
          });

        }, 300);
      }());
    }

    scene1();
  }

  const numkeys = 3;
  const sha1 = '26453f18b0c16646a987191d15cd816b816ebd47';

  exports.ready = function(server_id, channel_id, cb1, cb2){

    redis.evalsha(sha1, numkeys, conf.redis.selectDB, conf.app.id, '', server_id, channel_id, (new Date().getTime()), (err, doc) => {
      if(err) return cb1(err);
      cb1(null, doc);
      init(doc, cb2);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '4464a41134b22e88b85b379b3c3d67f0abc2d948';

  exports.shot = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();


(() => {
  const numkeys = 2;
  const sha1 = '4464a41134b22e88b85b379b3c3d67f0abc2d948';

  exports.blast = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '4464a41134b22e88b85b379b3c3d67f0abc2d948';

  exports.switch = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '4464a41134b22e88b85b379b3c3d67f0abc2d948';

  exports.tool = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();
