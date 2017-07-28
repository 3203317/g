/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const notice  = require('../controllers/notice');
const cfg     = require('../controllers/cfg');
const site    = require('../controllers/site');
const manager = require('../controllers/manager');
const user    = require('../controllers/user');
const fishjoy = require('../controllers/fishjoy');

module.exports = function(app){

  app.post('/manage/user/del$', manager.login_validate, user.del);
  app.post('/manage/user/edit$', manager.login_validate, user.edit);
  app.get('/manage/user/edit$', manager.login_validate, user.editUI);
  app.get('/manage/user/', manager.login_validate, user.indexUI);

  app.post('/manage/notice/del$', manager.login_validate, notice.del);
  app.post('/manage/notice/edit$', manager.login_validate, notice.edit);
  app.get('/manage/notice/edit$', manager.login_validate, notice.editUI);
  app.post('/manage/notice/add$', manager.login_validate, notice.add);
  app.get('/manage/notice/add$', manager.login_validate, notice.addUI);
  app.get('/manage/notice/', manager.login_validate, notice.indexUI);

  app.get('/manage/', manager.login_validate, site.indexUI);
  app.get('/manage/welcome$', manager.login_validate, site.welcomeUI);
  app.get('/manage/manager/profile$', manager.login_validate, manager.profileUI);
  app.post('/manage/manager/changePwd', manager.login_validate, manager.changePwd);
  app.get('/manage/manager/changePwd$', manager.login_validate, manager.changePwdUI);

  app.get('/manage/settings/', manager.login_validate, cfg.indexUI);
  app.post('/manage/settings/edit', manager.login_validate, cfg.edit);

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
