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

const md5 = require('speedt-utils').md5;
const utils = require('speedt-utils').utils;

const mysql = require('emag.db').mysql;
const redis = require('emag.db').redis;

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

(() => {
  var sql = 'SELECT a.* FROM s_user a WHERE a.device_code=?';

  /**
   * 没有则创建
   *
   * @return
   */
  exports.getByDeviceCode = function(device_code /* 设备号 */, cb){
    var self = this;

    mysql.query(sql, [device_code], (err, docs) => {
      if(err) return cb(err);
      if(mysql.checkOnly(docs)) return cb(null, docs[0]);

      var postData = {
        status: 1,
        device_code: device_code
      };

      self.saveDeviceCode(postData, (err, doc) => {
        if(err) return cb(err);
        cb(null, doc);
      });
    });
  };
})();

/**
 *
 * @params
 * @return
 */
(() => {
  var sql = 'INSERT INTO s_user (id, user_name, user_pass, status, weixin, mobile, create_time, device_code) values (?, ?, ?, ?, ?, ?, ?, ?)';

  exports.saveDeviceCode = function(newInfo, cb){
    if(err) return cb(err);

    // params
    var postData = [
      utils.replaceAll(uuid.v1(), '-', ''),
      newInfo.user_name || '',
      newInfo.user_pass || '',
      newInfo.status || 0,
      newInfo.weixin || '',
      newInfo.mobile || '',
      new Date(),
      newInfo.device_code
    ];

    mysql.query(sql, postData, function (err, status){
      if(err) return cb(err);
      cb(null, postData);
    });
  };
})();

/**
 * 游客登陆
 *
 * @return
 */

/**
 *
 * @return
 * @code 101 用户名或密码输入错误
 * @code 102 禁止登陆
 * @code 103 用户名或密码输入错误
 */
exports.login = function(logInfo /* 用户名及密码 */, cb){
  var self = this;

  self.getByName(logInfo.user_name, (err, doc) => {
    if(err) return cb(err);
    if(!doc) return cb(null, '101');

    // 用户的状态
    if(1 !== doc.status) return cb(null, '102');

    // 验证密码
    if(md5.hex(logInfo.user_pass) !== doc.user_pass)
      return cb(null, '103');

    var p1 = new Promise((resolve, reject) => {
      self.authorize(doc, (err, code) => {
          if(err) return reject(err);
          resolve(code);
      });
    });

    var p2 = new Promise((resolve, reject) => {
      // 服务器可用性
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
  const seconds = 5;  //令牌有效期 5s
  const numkeys = 4;
  const sha1 = '18e50d853ff22c5c77895418f6f7fc40777b387c';
  const client_id = '5a2c6a1043454b168e6d3e8bef5cbce2';

  /**
   * 令牌授权
   *
   * @param user_id
   * @param client_id
   * @return 登陆令牌
   */
  exports.authorize = function(doc, cb){
    var code = utils.replaceAll(uuid.v4(), '-', '');

    delete doc.user_pass;

    redis.evalsha(sha1, numkeys,
      conf.redis.database, client_id, doc.id, code,
      seconds, JSON.stringify(doc),
      doc.score,
      doc.tool_1,
      doc.tool_2,
      doc.tool_3,
      doc.tool_4,
      doc.tool_5,
      doc.tool_6,
      doc.tool_7,
      doc.tool_8,
      doc.tool_9,
      doc.bullet_level,
      (err, code) => {
        if(err) return cb(err);
        cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = 'c39a13fcb7b9f7f8d19e4541e7700dcece20f40a';

  /**
   *
   */
  exports.logout = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, (err, code) => {
        if(err) return cb(err);
        cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 3;
  const sha1 = 'f06832582a7fd8913d81dbd5a389bb245ef970b3';

  /**
   *
   */
  exports.myInfo = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, server_id, channel_id, (err, code) => {
        if(err) return cb(err);
        cb(null, code);
    });
  };
})();

(() => {
  const numkeys = 2;
  const sha1 = 'a9e6028acde5c177fe3634f7cb1016f29fd2e340';

  /**
   *
   */
  exports.userInfo = function(user_id, cb){

    redis.evalsha(sha1, numkeys, conf.redis.database, user_id, (err, code) => {
        if(err) return cb(err);
        cb(null, code);
    });
  };
})();