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
  const sha1 = '58ddebed52c5a6535a87b693dc1ddc9269491a7b';

  exports.search = function(server_id, channel_id, group_type, cb){

    redis.evalsha(sha1, numkeys, '1', utils.replaceAll(uuid.v1(), '-', ''), '', server_id, channel_id, group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '5dc63a3a4b5b8d01b1a2d507fbb62060628c9e25';

  exports.quit = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();