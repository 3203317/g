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

  biz.notice.findAll(function (err, docs){

    res.render('notice/index', {
      conf: conf,
      data: {
        list_notice:     docs,
        session_user: req.session.user,
        nav_choose:   ',04,0401,'
      }
    });
  });
};
