/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = {
  corp: {
    name: 'foreworld.net'
  },
  cookie: {
    secret: 'login'
  },
  html: {
    cdn: 'http://www.foreworld.net/'
  },
  emag: {
    mysql: {
      database: 'emag',
      host: '127.0.0.1',
      port: 22306,
      user: 'root',
      password: 'password',
      connectionLimit: 50
    },
    redis: {
      port: 22379,
      host: '127.0.0.1',
      password: '123456'
    }
  }
};