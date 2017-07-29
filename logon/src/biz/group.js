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

const _ = require('underscore');

(() => {
  const numkeys = 5;
  const sha1 = 'f9d221987f8003f97e5ce2a429d442c1903dea39';

  exports.search = function(server_id, channel_id, group_type, cb){

    if(!server_id)  return;
    if(!channel_id) return;
    if(!group_type) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, utils.replaceAll(uuid.v1(), '-', ''), group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = '052580cff3f665a9d4534bda0db92728c6f23fe8';

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
  const sha1 = 'e6ed84abc1e1fea7a5de4a8a69d05e6147b5268c';

  /**
   * group_users_ready.lua
   */
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
  const sha1 = 'adeefa381434d261f55cc20d420c0412f855b280';

  /**
   * group_users.lua
   */
  exports.allUsers = function(group_id, cb){

    if(!group_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = 'b03ad26d0631698ce29ed9804abaae900b7e610c';

  /**
   * group_users_channel.lua
   */
  exports.findUsersByChannel = function(server_id, channel_id, cb){

    if(!server_id) return;
    if(!channel_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();
