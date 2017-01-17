/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = {
  KEYWORDS: {
    BEFORE_FILTER: '__befores__',
    AFTER_FILTER: '__afters__',
  },

  FILEPATH: {
    SERVER: '/config/server.json',
    CRON: '/config/cron.json',
    LOG: '/config/log4js.json',
  },

  RESERVED: {
    START: 'start',
    AFTER_START: 'afterStart',
    BASE: 'base',
    ENV: 'env',
    ENV_DEV: 'development',
    ENV_PRO: 'production',
    SERVER_ID: 'serverId',
    SERVER_HOST: 'host',
    SERVER_PORT: 'port',
  }
};