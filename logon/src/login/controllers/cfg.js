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

  biz.cfg.findAll(1, function (err, docs){

    res.render('settings/index', {
      conf: conf,
      data: {
        list_cfg:     docs,
        session_user: req.session.user,
        nav_choose:   ',02,0201,'
      }
    });
  });
};

exports.edit = function(req, res, next){
  var query = req.body;

  biz.cfg.editInfo(query, (err, status) => {
    if(err) return next(err);
    res.send({});
  });
};