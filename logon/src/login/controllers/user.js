/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const URL = require('url');

const conf = require('../settings');
const utils = require('speedt-utils').utils;

const biz = require('emag.biz');

exports.register = function(req, res, next){
  var result = { success: false };
  var query  = req.body;

  biz.user.register(query, function (err, warn, doc){
    if(err) return next(err);
    if(warn){
      result.msg = warn;
      return res.send(result);
    }

    result.success = true;
    res.send(result);
  });
};

exports.loginUI = function(req, res, next){
  res.render('login', {
    conf: conf,
    title: '游客登陆 | '+ conf.corp.name,
    data: {}
  });
};

exports.login = function(req, res, next){
  var query = req.body;

  biz.user.login(query, (err, code, token /* 授权码及服务器信息 */) => {
    if(err) return next(err);
    if(code) return res.send({ error: { code: code } });
    res.send({ data: token });
  });
};
