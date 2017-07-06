/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const mysql = require('./emag/mysql');
const redis = require('./emag/redis');

/**
 *
 * @return 可用的服务器
 */
exports.search = function(server_id, channel_id, group_type, cb){
  console.log(arguments)
  cb(null, '681');
};