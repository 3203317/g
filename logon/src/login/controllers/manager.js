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

exports.changePwdUI = function(req, res, next){
  res.render('manager/changePwd', {
    conf: conf,
    title: '修改密码 | '+ conf.corp.name,
    data: {
      session_user: req.session.user,
      nav_choose:   ',03,0302,'
    }
  });
};

exports.profileUI = function(req, res, next){
  res.render('manager/profile', {
    conf: conf,
    title: '用户资料 | '+ conf.corp.name,
    data: {
      session_user: req.session.user,
      nav_choose:   ',03,0301,'
    }
  });
};

exports.loginUI = function(req, res, next){
  res.render('manager/login', {
    conf: conf,
    title: '管理登陆 | '+ conf.corp.name,
    data: {}
  });
};

exports.login = function(req, res, next){
  var query = req.body;

  biz.manager.login(query, (err, code, doc) => {
    if(err)  return next(err);
    if(code) return res.send({ error: { msg: code } });

    // session
    req.session.userId = doc.id;
    req.session.user = doc;

    res.send({});
  });
};

exports.login_validate = function(req, res, next){
  if(req.session.userId) return next();
  if(req.xhr) return res.send({ error: { msg: '无权访问' } });
  res.redirect('/manage/manager/login?refererUrl='+ escape(req.url));
};

exports.logoutUI = function(req, res, next){
  req.session.destroy();
  res.redirect('/manage/manager/login');
};
