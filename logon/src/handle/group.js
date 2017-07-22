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

exports.search = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('group search empty');

  try{
    var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports.quit = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('group quit empty');

  try{
    var data = JSON.parse(msg.body);
  }catch(ex){ return; }
};

exports._quit = function(client, server_id, channel_id, seq_id, cb){
  if(!client)     return;
  if(!server_id)  return;
  if(!channel_id) return;
  if(!seq_id)     return;
};
