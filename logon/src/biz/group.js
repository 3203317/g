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

const redis = require('emag.db').redis;

(() => {
  const numkeys = 5;
  const sha1 = '3d6f13ebb46927f7a9255aeb345c636ab7a413d2';

  exports.search = function(server_id, channel_id, group_type, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, utils.replaceAll(uuid.v1(), '-', ''), group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = '152fcba298eb51a7fa285d2061b1f576b995f2ce';

  exports.quit = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = 'd01631de4357c0d35b8e7477fd158a10014abbdc';

  exports.readyUsers = function(group_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = 'beebd502fa7a6b1918bfc1cdff757e50da048399';

  exports.allUsers = function(group_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();