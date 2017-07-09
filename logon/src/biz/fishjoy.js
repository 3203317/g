/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const mysql = require('./emag/mysql');
const redis = require('./emag/redis');

(() => {
  function init(group_id, cb){
    console.log('[INFO ] init thread');
  }

  const numkeys = 2;
  const sha1 = '162b2a12a984dcd7c5c9261f6d94d7668f9d724c';

  exports.ready = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '123456', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);

      switch(doc){
        case 'invalid_user_id':
        case 'invalid_group_id':
        case 'invalid_group_pos_id': return cb(null, doc);
        case 'back_running': return cb();
        default: init(doc, cb);
      }
    });
  };
})();