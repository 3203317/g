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


exports.indexUI = function(req, res, next){
  res.render('login', {
    conf: conf,
    title: '游客登陆 | '+ conf.corp.name,
    data: {}
  });  // render
};

/**
 * 1、返回access_token（用于登陆tcp服务器），有效期1分钟
 */
exports.index = function(req, res, next){
  var query = req.body;

  biz.user.login(query, (err, code, user) => {
    if(err) return next(err);
    if(code) return res.send({ error: { code: code } });
    res.send({ data: user });
  }); // login
};