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
  const numkeys = 2;
  const sha1 = '1a87978a835dec9253ae6f5de4436b6c8d3755f3';

  exports.ready = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '123456', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);
      cb(null, doc);
    });
  };
})();