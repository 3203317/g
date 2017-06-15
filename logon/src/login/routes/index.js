/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const login = require('../controllers/login');
const lobby = require('../controllers/lobby');

module.exports = function(app){
  app.get('/user/login$', login.indexUI);
  app.post('/user/login$', login.index);

  app.get('/', login.login_validate, lobby.indexUI);
};