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
  var sql = 'SELECT a.* FROM w_notice a ORDER BY a.create_time DESC';

  exports.findAll = function(cb){
    mysql.query(sql, null, (err, docs) => {
      if(err) return cb(err);
      cb(null, docs);
    });
  };
})();
