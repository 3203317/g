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

exports.index = function(req, res, next){
  var result = { success: false };
  var query = req.body;
  res.send(result);
};