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

exports.indexUI_params = function(req, res, next){
  var query = URL.parse(req.url, true).query;

  if(!utils.isEmpty(query.client_id)){
    return fn_err(res, { code: 'invalid_client_id' });
  }

  if(!utils.isEmpty(query.redirect_uri)){
    return fn_err(res, { code: 'invalid_redirect_uri' });
  }

  switch(query.response_type){
    case 'token':
    case 'code': return next();
    default: return fn_err(res, { code: 'unsupported_response_type' });
  }
};

exports.indexUI = function(req, res, next){
  var query = URL.parse(req.url, true).query;

  biz.user_app.getUserAuth(query.client_id, (err, doc) => {
    if(err) return next(err);
    if(!doc) return fn_err(res, { code: 'invalid_client' });

    res.render('login', {
      conf: conf,
      title: '应用授权 | '+ conf.corp.name,
      data: {
        query: query,
        user_app: doc
      }
    });  // render
  });  // getUserAuth
};

const fn_err = (res, err) => {
  res.render('login_error', {
    conf: conf,
    title: '第三方授权 | '+ conf.corp.name,
    err: err
  });
};