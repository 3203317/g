/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = {
  id: '1234567890',
  corp: {
    name: 'foreworld.net',
  },
  cookie: {
    key: 'login',
    secret: 'login'
  },
  html: {
    cdn: 'http://www.foreworld.net/'
  },
  activemq: {
    host: '127.0.0.1',
    port: 12613,
    user: 'admin',
    password: 'admin',
  },
  mysql: {
    database: 'emag',
    host: '127.0.0.1',
    port: 12306,
    user: 'root',
    password: 'password',
    connectionLimit: 50
  },
  redis: {
    port: 12379,
    host: '127.0.0.1',
    password: '123456',
    selectDB: 1
  }
};