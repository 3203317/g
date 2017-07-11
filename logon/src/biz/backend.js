/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const db = require('emag.db');
const mysql = db.mysql;
const redis = db.redis;

(() => {
  const numkeys = 2;
  const sha1 = '232eca1f1b0411d8be28f714c23cf2f2e52176e1';

  /**
   *
   */
  exports.open = function(back_id, cb){

    redis.evalsha(sha1, numkeys, '1', '', back_id, (new Date().getTime()), (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 1;
  const sha1 = '232eca1f1b0411d8be28f714c23cf2f2e52176e1';

  /**
   *
   */
  exports.close = function(back_id, cb){

    redis.evalsha(sha1, numkeys, '1', back_id, (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();