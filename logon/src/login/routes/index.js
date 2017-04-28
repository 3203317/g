/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const authorize = require('../controllers/authorize');
const login = require('../controllers/login');

module.exports = function(app){
  app.post('/', authorize.index_params, authorize.index);
  app.get('/', login.indexUI_params, login.indexUI);
};