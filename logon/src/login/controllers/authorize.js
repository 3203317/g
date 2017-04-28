/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const utils = require('speedt-utils').utils;

const biz = require('emag.biz');

const conf = require('../settings');

exports.index = function(req, res, next){
  var query = req.body;

  biz.user.login(query, (err, code, user) => {
    if(err) return next(err);
    if(code) return fn_err(res, { code: code });

    biz.user_app.getUserAuth(query.client_id, (err, doc) => {
      if(err) return next(err);
      if(!doc) return fn_err(res, { code: 'invalid_client' });

      biz.user_app.authorize({
        client_id: query.client_id,
        redirect_uri: query.redirect_uri,
        user_id: user.id
      }, (err, code) => {
        if(err) return next(err);
        if(!code) return fn_err(res, { code: 'invalid_authorize' });

        var uri = [];
        uri.push(query.redirect_uri);
        uri.push('?code='+ code);
        uri.push('&state='+ query.state);
        res.redirect(302, uri.join(''));
      });  // authorize
    });  // getUserAuth
  });  // login
};

exports.index_params = function(req, res, next){
  var query = req.body;

  if(!utils.isEmpty(query.client_id)){
    return fn_err(res, { code: 'invalid_client_id' });
  }

  if(!utils.isEmpty(query.redirect_uri)){
    return fn_err(res, { code: 'invalid_redirect_uri' });
  }

  switch(query.response_type){
    case 'code': return next();
    case 'token':
    default: return fn_err(res, { code: 'unsupported_response_type' });
  }
};

const fn_err = (res, err) => {
  res.render('authorize_error', {
    conf: conf,
    title: '第三方授权 | '+ conf.corp.name,
    err: err
  });
};