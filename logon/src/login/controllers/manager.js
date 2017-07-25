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

exports.loginUI = function(req, res, next){
  res.render('back/login', {
    conf: conf,
    title: '管理登陆 | '+ conf.corp.name,
    data: {}
  });
};

exports.login = function(req, res, next){
  var query = req.body;

  biz.manager.login(query, (err, warn, doc) => {
    if(err) return next(err);
    if(warn) return res.send({ error: { msg: warn } });
    res.send({});
  });
};
