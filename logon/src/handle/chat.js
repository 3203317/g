/*!
 * emag.handle
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const log4js = require('log4js');
const logger = log4js.getLogger('handle');

exports.one_for_one = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('chat one_for_one empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }

  if(!data.serverId)  return;
  if(!data.channelId) return;

  data.method = 2002;
  data.receiver = data.channelId;

  logger.debug('chat one_for_one: %j', data);

  if(client) client.send('/queue/back.send.v2.'+ data.serverId, { priority: 9 }, JSON.stringify(data));
};
