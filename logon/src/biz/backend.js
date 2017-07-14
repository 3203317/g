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

const mysql = require('emag.db').mysql;
const redis = require('emag.db').redis;

(() => {
  const numkeys = 2;
  const sha1 = '95e2ea527b9b471c4c738f53e41a02187fb7b68b';

  /**
   *
   */
  exports.open = function(back_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, back_id, (new Date().getTime()), (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = 'f7f5e836961699048db5e9f4cd0ee48c074098bb';

  /**
   *
   */
  exports.close = function(back_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, back_id, (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();