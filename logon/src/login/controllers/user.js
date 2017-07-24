/*!
 * emag.user
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const URL = require('url');

const conf = require('../settings');
const utils = require('speedt-utils').utils;

const biz = require('emag.biz');

exports.register = function(req, res, next){
  var result = { success: false },
      data   = req._data;

  biz.user.register(data, function (err, warn, doc){
    if(err) return next(err);
    if(warn){
      result.msg = warn;
      return res.send(result);
    }

    result.success = true;
    res.send(result);
  });
};
