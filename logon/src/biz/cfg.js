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
  var sql = 'SELECT a.* FROM s_cfg a WHERE a.status=? ORDER BY a.key_ ASC';

  exports.findAll = function(status, cb){
    mysql.query(sql, [status || 0], (err, docs) => {
      if(err) return cb(err);
      cb(null, docs);
    });
  };
})();

(() => {
  var sql = 'UPDATE s_cfg SET value_=? WHERE key_=? AND type_=?';

  exports.editInfo = function(newInfo, cb){

    var postData = [
      newInfo.value_,
      newInfo.key_,
      newInfo.type_
    ];

    mysql.query(sql, postData, function (err, status){
      if(err) return cb(err);
      cb(null, status);
    });
  };
})();
