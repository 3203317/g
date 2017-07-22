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


function _ready_ready(client, server_id, channel_id, seq_id, err, doc){
  if(err) return logger.error('fishjoy ready:', err);

  if(_.isArray(doc)){

    var arr1 = doc[0];
    if(!arr1) return;

    var result = {
      method: 5006,
      seqId:  seq_id,
      data:   doc[1],
    };

    return ((function(){

      for(let i=0, j=arr1.length; i<j; i++){
        let s           = arr1[i];
        result.receiver = arr1[++i];

        if(!s)               continue;
        if(!result.receiver) continue;

        client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }
    })());
  }

  switch(doc){
    case 'invalid_user_id':
      return client.send('/queue/front.force.v2.'+ server_id, { priority: 9 }, channel_id);
    default: break;
  }
}

exports.ready = function(client, msg){
  if(!_.isString(msg.body)) return logger.error('fishjoy ready empty');

  try{ var data = JSON.parse(msg.body);
  }catch(ex){ return; }

  biz.fishjoy.ready(data.serverId, data.channelId,
    _ready_ready.bind(null, client, data.serverId, data.channelId, data.seqId));
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
