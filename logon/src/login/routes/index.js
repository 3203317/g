/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const manager = require('../controllers/manager');
const user = require('../controllers/user');
const fishjoy = require('../controllers/fishjoy');

module.exports = function(app){
  app.get('/manager/login$', manager.loginUI);
  app.post('/manager/login$', manager.login);

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
