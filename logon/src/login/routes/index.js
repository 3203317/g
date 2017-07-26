/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const site    = require('../controllers/site');
const manager = require('../controllers/manager');
const user    = require('../controllers/user');
const fishjoy = require('../controllers/fishjoy');

module.exports = function(app){

  app.get('/manage/', manager.login_validate, site.indexUI);
  app.get('/manage/welcome$', manager.login_validate, site.welcomeUI);

  app.get('/manage/manager/login$', manager.loginUI);
  app.post('/manage/manager/login$', manager.login);
  app.get('/manage/manager/logout$', manager.logoutUI);

  app.get('/user/login$', user.loginUI);
  app.post('/user/login$', user.login);

  app.post('/user/register$', user.register);

  app.get('/', fishjoy.indexUI);
};

function valiPostData(req, res, next){
  var data = req.body.data;

  try{
    data = JSON.parse(data);
    if('object' === typeof data){
      req._data = data;
      return next();
    }
    res.send({ success: false });
  }catch(ex){
    res.send({ success: false, msg: ex.message });
  }
}
