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
  const sha1 = '317723bd8b6dd58b2357e9927966e0e58fcf361a';

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
  const sha1 = '7a64cc3fff290ed34d2f07244f02a688753e9c59';

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
  const sha1 = 'd2d779fede551511656eec6ce04c311c8590f712';

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
  const sha1 = 'a38933548d27b93b133bdf3e025d05fad4ca368d';

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
