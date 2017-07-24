/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const user = require('../controllers/user');
const fishjoy = require('../controllers/fishjoy');

module.exports = function(app){
  app.get('/user/login$', user.loginUI);
  app.post('/user/login$', user.login);

  app.get('/', fishjoy.indexUI);
};