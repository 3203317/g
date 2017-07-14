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

(() => {
  const numkeys = 3;
  const sha1 = '13343e5c8bfa165358180fc9dc6f431c79394174';

  exports.search = function(server_id, channel_id, group_type, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, utils.replaceAll(uuid.v1(), '-', ''), '', server_id, channel_id, group_type, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '87d4df936cb2619402ce915046a7e5f1bb6f1e32';

  exports.quit = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, '', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();

(() => {
  const numkeys = 1;
  const sha1 = '73a7592a7cafc09fb52436ba74d7f60ce58300c4';

  exports.users = function(group_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, group_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();