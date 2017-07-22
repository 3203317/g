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
  const sha1 = '1101daa0c0023c3e9ea53f628c0f0c6f4e0f69e0';

  exports.search = function(server_id, channel_id, group_type, cb){

    if(!group_type) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, utils.replaceAll(uuid.v1(), '-', ''), group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = '7fbbaf4c6307bb9936653d65ca4cd7791a52a072';

  exports.quit = function(server_id, channel_id, cb){

    if(!server_id)  return;
    if(!channel_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '921cfe8c0a29346c8fe452033abc7b6629296dd0';

  exports.readyUsers = function(group_id, cb){

    if(!group_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '7d7d9b45f97db7bebc5b1f870cae415a73281bd9';

  exports.allUsers = function(group_id, cb){

    if(!group_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();
