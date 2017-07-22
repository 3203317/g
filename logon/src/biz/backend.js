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
  const sha1 = '5e360796e9e10f02db4e018d9c77f756c7f580fd';

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
  const sha1 = 'f7f5e836961699048db5e9f4cd0ee48c074098bb';

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