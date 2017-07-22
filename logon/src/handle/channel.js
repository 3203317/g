/*!
 * emag.handle
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const log4js = require('log4js');
const logger = log4js.getLogger('handle');

const _ = require('underscore');

const handle = require('./');

exports.open = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('channel open empty');

  var s = msg.body.split('::');

  biz.user.myInfo(s[0], s[1], function (err, doc){
    if(err) return logger.error('channel open:', err);
    if(!_.isArray(doc)) return;

    var user = cfg.arrayToObject(doc);

    var sb = {
      method: 1,
      seqId: 1,
      receiver: s[1],
      data: JSON.parse(user.extend_data),
    };

    if(client) client.send('/queue/back.send.v2.'+ s[0], { priority: 9 }, JSON.stringify(sb));
  });
};

exports.open = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('channel close empty');

  var s = msg.body.split('::');

  handle.group.quit(s[0], s[1], 0, function (err){
    if(err) return logger.error('group quit:', err);
    logger.info('group quit: %j', s);

    biz.user.logout(s[0], s[1], function (err, code){
      if(err) return logger.error('channel close:', err);
      logger.info('channel close: %j', s);
    });
  });
};
