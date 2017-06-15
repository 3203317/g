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
  });
};

exports.index = function(req, res, next){
  var query = req.body;

  biz.user.login(query, (err, code, token /* 授权码及服务器信息 */) => {
    if(err) return next(err);
    if(code) return res.send({ error: { code: code } });
    res.send({ data: token });
  });
};

// /**
//  * 用户会话验证
//  *
//  * @params
//  * @return
//  */
// exports.login_validate = function(req, res, next){
//   if(3 === req.session.lv) return next();
//   if(req.xhr) return res.send({ success: false, msg: '无权访问' });
//   res.redirect('/client/user/login?refererUrl='+ escape(req.url));
// };