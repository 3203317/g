/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const md5 = require('speedt-utils').md5;
const utils = require('speedt-utils').utils;

const mysql = require('./emag/mysql');
const redis = require('./emag/redis');

const server = require('./server');

(() => {
  var sql = 'SELECT a.* FROM s_user a WHERE a.user_name=?';

  /**
   *
   * @return
   */
  exports.getByName = function(user_name, cb){
    mysql.query(sql, [user_name], (err, docs) => {
      if(err) return cb(err);
      cb(null, mysql.checkOnly(docs) ? docs[0] : null);
    });
  };
})();

(() => {
  var sql = 'SELECT a.* FROM s_user a WHERE a.id=?';

  /**
   *
   * @return
   */
  exports.getById = function(id, cb){
    mysql.query(sql, [id], (err, docs) => {
      if(err) return cb(err);
      cb(null, mysql.checkOnly(docs) ? docs[0] : null);
    });
  };
})();

/**
 *
 * @return
 * @code 10001 用户名或密码输入错误
 * @code 10002 禁止登陆
 * @code 10003 用户名或密码输入错误
 */
exports.login = function(logInfo, cb){
  var self = this;

  self.getByName(logInfo.user_name, (err, doc) => {
    if(err) return cb(err);
    if(!doc) return cb(null, '10001');
    if(1 !== doc.status) return cb(null, '10002');

    if(md5.hex(logInfo.user_pass) !== doc.user_pass)
      return cb(null, '10003');

    var p1 = new Promise((resolve, reject) => {
      self.authorize(doc.id, 'e0b13571d00d4606b9570415423cb5be', (err, code) => {
        if(err) return reject(err);
        resolve(code);
      });
    });

    var p2 = new Promise((resolve, reject) => {
      server.available((err, info) => {
        if(err) return reject(err);
        resolve(info);
      });
    });

    Promise.all([p1, p2]).then(values => {
      cb(null, null, values);
    }).catch(cb);

  });
};

(() => {
  const seconds = 60 * 1;
  const numkeys = 4;
  const sha1 = '3d678e5959bb30e7a1446031b8084bff384b8bd9';

  /**
   *
   *
   * @param user_id
   * @param client_id
   * @return 登陆令牌
   */
  exports.authorize = function(user_id, client_id, cb){
    var code = utils.replaceAll(uuid.v4(), '-', '');

    redis.evalsha(sha1, numkeys, 'code', 'user_id', 'client_id', 'seconds', code, user_id, client_id, seconds, (err, code) => {
      if(err) return cb(err);
      cb(null, code);
    });
  };
})();