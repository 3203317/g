/*!
 * emag.backend
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const conf = require('./settings');

const biz = require('emag.biz');

process.on('uncaughtException', err => {
  console.error('[ERROR] %s', err);
});

process.on('exit', () => {

  biz.backend.close(conf.app.id, (err, code) => {
    if(err) return console.error('[ERROR] backend %j close: %s', conf.app.id, err);
    console.info('[INFO ] backend %j close: %s', conf.app.id, code);
  });

});

(() => {
  biz.backend.open(conf.app.id, (err, code) => {
    if(err) return console.error('[ERROR] backend %j open: %s', conf.app.id, err);
    console.info('[INFO ] backend %j open: %s', conf.app.id, code);
  });
})();

// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------

(function(){

  var activemq = conf.activemq;

  var Stomp = require('stompjs');
  var client = Stomp.overTCP(activemq.host, activemq.port);

  var onCb = function(frame){
    _front_start = client.subscribe('/queue/front.start', on_front_start);
    _front_stop = client.subscribe('/queue/front.stop', on_front_stop);

    _channel_open = client.subscribe('/queue/channel.open', on_channel_open);
    _channel_close = client.subscribe('/queue/channel.close', on_channel_close);

    _2001_chat_1v1 = client.subscribe('/queue/qq.2001', on_2001_chat_1v1);

    _3001_group_search = client.subscribe('/queue/qq.3001', on_3001_group_search);
    _3005_group_quit = client.subscribe('/queue/qq.3005', on_3005_group_quit);

    _5001_fishjoy_shot = client.subscribe('/queue/qq.5001', on_5001_fishjoy_shot);
    _5013_fishjoy_switch = client.subscribe('/queue/qq.5013', on_5013_fishjoy_switch);

    _5003_fishjoy_blast = client.subscribe('/queue/qq.5003.'+ conf.app.id, on_5003_fishjoy_blast);
    _5005_fishjoy_ready = client.subscribe('/queue/qq.5005', on_5005_fishjoy_ready);
    _5011_fishjoy_tool = client.subscribe('/queue/qq.5011.'+ conf.app.id, on_5011_fishjoy_tool);
  };

  function _unsubscribe(){
    _front_start.unsubscribe();
    _front_stop.unsubscribe();

    _channel_open.unsubscribe();
    _channel_close.unsubscribe();

    _2001_chat_1v1.unsubscribe();

    _3001_group_search.unsubscribe();
    _3005_group_quit.unsubscribe();

    _5001_fishjoy_shot.unsubscribe();
    _5013_fishjoy_switch.unsubscribe();

    _5003_fishjoy_blast.unsubscribe();
    _5005_fishjoy_ready.unsubscribe();
    _5011_fishjoy_tool.unsubscribe();
  }

  var onErr = function(err){
    _unsubscribe();
    console.error(':: %s', err);
  };

  process.on('uncaughtException', err => {
    _unsubscribe();
  });

  process.on('exit', () => {
    _unsubscribe();
  });

  const headers = {
    login: activemq.user,
    passcode: activemq.password,
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _front_start, _front_stop;

  var on_front_start = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');
    console.info('[INFO ] front start: %s', msg.body);
  };

  var on_front_stop = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');
    console.info('[INFO ] front stop: %s', msg.body);
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _channel_open, _channel_close;

  var on_channel_open = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var s = msg.body.split('::');

    var b = {
      method: 1,
      seqId: 1,
      receiver: s[1]
    };

    biz.user.myInfo(s[0], s[1], function (err, doc){
      if(err) return console.error('[ERROR] channel open: %s', err);

      b.data = doc;
      client.send('/queue/back.send.v2.'+ s[0], { priority: 9 }, JSON.stringify(b));
    });

  };

  var on_channel_close = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var s = msg.body.split('::');

    _on_3005_group_quit(s[0], s[1], 0, function (err){
      if(err) return console.error('[ERROR] group quit: %s', err);
      console.info('[INFO ] group quit: %j', s);

      biz.user.logout(s[0], s[1], function (err, code){
        if(err) return console.error('[ERROR] channel close: %s', err);
        console.info('[INFO ] channel close: %j', s);
      });
    });
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _2001_chat_1v1;

  var on_2001_chat_1v1 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    console.info('[INFO ] chat 1v1 send: %j', data);

    data.method = 2002;
    data.receiver = data.channelId;

    client.send('/queue/back.send.v2.'+ data.serverId, { priority: 9 }, JSON.stringify(data));
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _3001_group_search, _3005_group_quit;

  var on_3001_group_search = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    _on_3005_group_quit(data.serverId, data.channelId, data.seqId, function (err){
      if(err) return console.error('[ERROR] %s', err);

      var group_type = data.data;

      biz.group.search(data.serverId, data.channelId, group_type, function (err, doc){
        if(err) return console.error('[ERROR] %s', err);

        switch(doc){
          case 'invalid_user_id':
          case 'invalid_group_type':
            return client.send('/queue/front.force.v2.'+ data.serverId, { priority: 9 }, data.channelId);
          case 'non_idle_group':
            return;
        }

        var result = {
          method: 3002,
          seqId: data.seqId,
          data: doc[1],
        };

        var arr = doc[0];

        for(let i=0, j=arr.length; i<j; i++){
          let s = arr[i];
          result.receiver = arr[++i];
          if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
        }

      });
    });
  };

  var on_3005_group_quit = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    _on_3005_group_quit(data.serverId, data.channelId, 0, function (err){
      if(err) return console.error('[ERROR] %s', err);
      console.info('[INFO ] group close: %j', data);
    });
  };

  var _on_3005_group_quit = function(server_id, channel_id, seq_id, cb){

    biz.group.quit(server_id, channel_id, function (err, doc){
      if(err) return cb(err);

      switch(doc){
        case 'invalid_user_id':
          client.send('/queue/front.force.v2.'+ server_id, { priority: 9 }, channel_id);
        case 'OK': return cb();
      }

      cb();

      var result = {
        method: 3006,
        seqId: seq_id,
        data: doc[1],
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){
        let s = arr[i];
        result.receiver = arr[++i];
        if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _5001_fishjoy_shot, _5013_fishjoy_switch;
  var _5003_fishjoy_blast, _5005_fishjoy_ready, _5011_fishjoy_tool;

  var on_5001_fishjoy_shot = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);
    var shot = JSON.parse(data.data);

    biz.fishjoy.shot(data.serverId, data.channelId, shot, function (err, doc){
      if(err) return console.error('[ERROR] %s', err);

      var result = {
        method: 5002,
        seqId: data.seqId,
        data: doc[1],
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){
        var s = arr[i];
        result.receiver = arr[++i];
        if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
  };

  var on_5003_fishjoy_blast = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);
    var blast = JSON.parse(data.data);

    biz.fishjoy.blast(data.serverId, data.channelId, blast, function (err, doc){
      if(err) return console.error('[ERROR] %s', err);

      var result = {
        method: 5004,
        seqId: data.seqId,
        data: doc[1],
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){
        var s = arr[i];
        result.receiver = arr[++i];
        if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
  };

  var _on_5005_fishjoy_ready_cb1 = function(server_id, channel_id, seq_id, err, doc){
    if(err) return console.error('[ERROR] fishjoy ready: %s', err);

    switch(doc){
      case 'invalid_group_id':
      case 'invalid_group_pos_id':
      case 'already_raise_hand': return;
      case 'invalid_user_id': return client.send('/queue/front.force.v2.'+ server_id, { priority: 9 }, channel_id);
    }

    var result = {
      method: 5006,
      seqId: seq_id,
      data: [doc[1].split('::')[2], doc[2]]
    };

    var arr = doc[0];

    for(let i=0, j=arr.length; i<j; i++){
      let s = arr[i];
      result.receiver = arr[++i];
      if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
    }
  };

  var _on_5005_fishjoy_ready_cb2 = function(seq_id, err, doc){
    if(err) return console.error('[ERROR] %s', err);

    var result = {
      timestamp: new Date().getTime(),
      method: 5008,
      seqId: seq_id,
      data: doc[1],
    };

    var arr = doc[0];

    for(let i=0, j=arr.length; i<j; i++){
      let s = arr[i];
      result.receiver = arr[++i];
      if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
    }
  };

  var _on_5005_fishjoy_ready_cb3 = function(seq_id, err, doc){
    if(err) return console.error('[ERROR] %s', err);

    var result = {
      timestamp: new Date().getTime(),
      method: 5010,
      seqId: seq_id,
    };

    var arr = doc;

    for(let i=0, j=arr.length; i<j; i++){
      let s = arr[i];
      result.receiver = arr[++i];
      if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
    }
  };

  var on_5005_fishjoy_ready = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    biz.fishjoy.ready(data.serverId, data.channelId,
      _on_5005_fishjoy_ready_cb1.bind(null, data.serverId, data.channelId, data.seqId),
      _on_5005_fishjoy_ready_cb2.bind(null, data.seqId),
      _on_5005_fishjoy_ready_cb3.bind(null, data.seqId));
  };

  var on_5013_fishjoy_switch = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);
    var level = data.data;

    biz.fishjoy.switch(data.serverId, data.channelId, level, function (err, doc){
      if(err) return console.error('[ERROR] %s', err);

      var result = {
        method: 5014,
        seqId: data.seqId,
        data: doc[1],
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){
        var s = arr[i];
        result.receiver = arr[++i];
        if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
  };

  var on_5011_fishjoy_tool = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);
    var tool_id = data.data;

    biz.fishjoy.tool(data.serverId, data.channelId, tool_id, function (err, doc){
      if(err) return console.error('[ERROR] %s', err);

      var result = {
        method: 5012,
        seqId: data.seqId,
        data: doc[1],
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){
        var s = arr[i];
        result.receiver = arr[++i];
        if(s) client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
  };

  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  client.connect(headers, onCb, onErr);
})();