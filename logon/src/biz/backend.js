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

const _ = require('underscore');

(() => {
  const numkeys = 2;
  const sha1 = '19ca6887d336b78f1edbf9954d58d75f842c07a3';

  /**
   * back_open.lua
   */
  exports.open = function(back_id, cb){

    if(!back_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, back_id, _.now(), (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = '5e68fe02ad9ffc41f3cfa3d1b85212b8ab370bef';

  /**
   * back_close.lua
   */
  exports.close = function(back_id, cb){

    if(!back_id) return;

    redis.evalsha(sha1, numkeys, conf.redis.database, back_id, (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();