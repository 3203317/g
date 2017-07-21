/*!
 * emag.handle
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const log4js = require('log4js');
const logger = log4js.getLogger('handle');

exports.start = function(msg){
  if(!msg.body) return logger.error('front start empty');
  logger.info('front start: %s', msg.body);
};

exports.stop = function(msg){
  if(!msg.body) return logger.error('front stop empty');
  logger.info('front stop: %s', msg.body);
};
