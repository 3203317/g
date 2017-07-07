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
  const numkeys = 3;
  const sha1 = 'c68bf0ef1ca151b31c767c30659adcf0417c6daf';

  exports.search = function(server_id, channel_id, group_type, cb){

    redis.evalsha(sha1, numkeys, '1', utils.replaceAll(uuid.v1(), '-', ''), '', server_id, channel_id, group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = 'f4f509bb76b663af82bb812d8eb81dfb89e02b4e';

  exports.quit = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();