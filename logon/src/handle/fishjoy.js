/*!
 * emag.handle
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const biz    = require('emag.biz');
const cfg    = require('emag.cfg');

const log4js = require('log4js');
const logger = log4js.getLogger('handle');

const _ = require('underscore');

exports.ready = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy ready empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports.switch = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy switch empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports.shot = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy shot empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports.blast = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy blast empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports.tool = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy tool empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};
