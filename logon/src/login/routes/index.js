/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const login = require('../controllers/login');
const fishjoy = require('../controllers/fishjoy');

module.exports = function(app){
  app.get('/user/login$', login.indexUI);
  app.post('/user/login$', login.index);

  app.get('/', fishjoy.indexUI);
};