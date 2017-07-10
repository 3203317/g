/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const db = require('emag.db');
const redis = db.redis;

const conf = require(path.join(cwd, 'settings'));

(() => {

  function init(group_info, cb){
    console.log('[INFO ] init');

    var s = group_info.split('::');

    var opts = {
      type: s[0],
      id: s[1],
      capacity: s[2]
    };

    // var curr_fish_list = [/*{
    //   id: 'uuid_1',
    //   type: '鲨鱼',
    //   weight: 20
    // },{
    //   id: 'uuid_2',
    //   type: '鳄鱼',
    //   weight: 15
    // }*/];

    // var prop_fish_type = [{
    //   type: '鲨鱼',
    //   probability: 1,
    //   weight: 20
    // }, {
    //   type: '鳄鱼',
    //   probability: 2,
    //   weight: 15
    // }];

    // var opts = {
    //   scene: scene,
    //   prop_group: prop_group,
    //   curr_fish_list: curr_fish_list,
    //   prop_fish_type: prop_fish_type
    // };

    fishbowlPool.create(opts, function (err, fishbowl){
      if(err) return cb(err);

      (function schedule(){
        var timeout = setTimeout(function(){

          clearTimeout(timeout);

          fishbowl.push(function (err, fishs){
            if(err) return cb(err);

            cb(null, null, fishs);
            schedule();
          });

        }, 300);

      }());

    });
  }

  const numkeys = 2;
  const sha1 = '45ed245f475c604374abccb8dac3b51ffe93af98';

  exports.ready = function(server_id, channel_id, cb1, cb2){

    redis.evalsha(sha1, numkeys, conf.emag.redis.selectDB, conf.id, server_id, channel_id, (err, doc) => {
      if(err) return cb(err);

      switch(doc){
        case 'invalid_user_id':
        case 'invalid_group_id':
        case 'invalid_group_pos_id':
          return cb1(null, doc);
      }

      if(-1 == doc.indexOf('::')) return cb1(null, doc);
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
