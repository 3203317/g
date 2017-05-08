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
  res.render('lobby/1_0_1', {
    conf: conf,
    title: '大厅 | '+ conf.corp.name,
    data: {}
  });  // render
};