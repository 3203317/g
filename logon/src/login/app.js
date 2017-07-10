/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const express = require('express'),
      velocity = require('velocityjs'),
      fs = require('fs'),
      http = require('http'),
      path = require('path'),
      cwd = process.cwd();

const macros = require('./lib/macro'),
      conf = require('./settings');

const app = express();

/* all environments */
app.set('port', process.env.PORT || 8888)
   .set('views', path.join(__dirname, 'views'))
   .set('view engine', 'html')
   /* use */
   .use(express.favicon())
   .use(express.json())
   .use(express.urlencoded())
   // .use(express.cookieParser())
   .use(express.methodOverride());

/* production */
if('production' === app.get('env')){
  app.use('/public', express.static(path.join(__dirname, 'public'), { maxAge: 10 * 1000 }))
     .use(express.errorHandler())
     .use(express.logger('dev'));
}

/* development */
if('development' === app.get('env')){
  app.use(express.logger('dev'))
     .use('/public', express.static(path.join(__dirname, 'public')))
     .use(express.errorHandler({
        dumpExceptions: true,
        showStack: true
      }));
}

// app.use(express.session({
//   secret: conf.cookie.secret,
//   key: conf.cookie.key,
//   cookie: {
//     maxAge: 1000 * 60 * 60 * 24 * 1  //1 days
//   }
// }));

app.use(app.router)
    /* velocity */
   .engine('.html', (path, options, fn) => {
      fs.readFile(path, 'utf8', (err, data) => {
        if(err) return fn(err);
        try{ fn(null, velocity.render(data, options, macros)); }
        catch(ex){ fn(ex); }
      });
    });

var server = http.createServer(app);
/* server.setTimeout(5000); */
server.listen(app.get('port'), () => {
  console.info('[INFO ] login server listening on port %s.', app.get('port'));
  require('./routes')(app);
});

process.on('uncaughtException', err => {
  console.error('[ERROR] %s', err);
});

process.on('exit', () => {
  // todo
});

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
  const biz = require('emag.biz');

  var activemq = conf.activemq;

  var Stomp = require('stompjs');
  var client = Stomp.overTCP(activemq.host, activemq.port);

  var onErr = function(err){
    _unsubscribe();
    console.error(':: %s', err);
  };

  var onCb = function(frame){
    _front_start = client.subscribe('/queue/front.start', on_front_start);
    _front_stop = client.subscribe('/queue/front.stop', on_front_stop);

    _channel_open = client.subscribe('/queue/channel.open', on_channel_open);
    _channel_close = client.subscribe('/queue/channel.close', on_channel_close);

    _2001_chat_1v1 = client.subscribe('/queue/qq.2001', on_2001_chat_1v1);

    _3001_group_search = client.subscribe('/queue/qq.3001', on_3001_group_search);
    _3005_group_quit = client.subscribe('/queue/qq.3005', on_3005_group_quit);

    _5001_fishjoy_shot = client.subscribe('/queue/qq.5001', on_5001_fishjoy_shot);
    _5003_fishjoy_blast = client.subscribe('/queue/qq.5003', on_5003_fishjoy_blast);
    _5005_fishjoy_ready = client.subscribe('/queue/qq.5005', on_5005_fishjoy_ready);
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
    _5003_fishjoy_blast.unsubscribe();
    _5005_fishjoy_ready.unsubscribe();
  }

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
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------
  // ----------------------------------------------------------------------------------------------------

  var _channel_open, _channel_close;

  var on_channel_open = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var s = msg.body.split('::');

    console.info('[INFO ] channel open: %j', s);

    var b = {
      version: 102,
      seqId: 1,
      timestamp: new Date().getTime(),
      receiver: s[1]
    };

    client.send('/queue/back.send.v2.'+ s[0], { priority: 9 }, JSON.stringify(b));
  };

  var on_channel_close = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var s = msg.body.split('::');

    console.info('[INFO ] channel close: %j', s);

    on_3005_quit(s[0], s[1], 0, function (err){
      if(err) return console.error('[ERROR] %s', err);
    });
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

  var _2001_chat_1v1;

  var on_2001_chat_1v1 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    console.log('[INFO ] chat 1v1 send: %j', data);

    data.method = 2002;
    data.receiver = data.channelId;

    client.send('/queue/back.send.v2.'+ data.serverId, { priority: 9 }, JSON.stringify(data));
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

  var _3001_group_search, _3005_group_quit;

  var on_3001_group_search = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    on_3005_quit(data.serverId, data.channelId, data.seqId, function (err){
      if(err) return console.error('[ERROR] %s', err);

      var group_type = JSON.parse(data.data).groupType;

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
          version: 102,
          method: 3002,
          seqId: data.seqId,
          timestamp: new Date().getTime(),
          data: JSON.stringify(doc[1])
        };

        var arr = doc[0];

        for(let i=0, j=arr.length; i<j; i++){

          var s = arr[i];

          result.receiver = arr[++i];

          client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
        }

      });
    });
  };

  var on_3005 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    on_3005_quit(data.serverId, data.channelId, 0, function (err){
      if(err) return console.error('[ERROR] %s', err);
    });
  };

  var on_3005_quit = function(server_id, channel_id, seq_id, cb){

    biz.group.quit(server_id, channel_id, function (err, doc){
      if(err) return cb(err);

      switch(doc){
        case 'invalid_user_id':
          client.send('/queue/front.force.v2.'+ server_id, { priority: 9 }, channel_id);
          return cb(doc);
        case 'OK':
          return cb();
      }

      cb();

      var result = {
        version: 102,
        method: 3006,
        seqId: seq_id,
        timestamp: new Date().getTime(),
        data: JSON.stringify(doc[1])
      };

      var arr = doc[0];

      for(let i=0, j=arr.length; i<j; i++){

        var s = arr[i];

        result.receiver = arr[++i];

        client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
      }

    });
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

  var _5001_fishjoy_shot, _5003_fishjoy_blast, _5005_fishjoy_ready;

  /**
   * shot
   */
  var on_5001_fishjoy_shot = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

  };

  /**
   * blast
   */
  var on_5003_fishjoy_blast = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

  };

  var on_fishjoy_5005_cb1 = function(server_id, channel_id, seq_id, err, code){
    if(err) return console.error('[ERROR] %s', err);

    switch(code){
      case 'invalid_user_id':
        return client.send('/queue/front.force.v2.'+ data.serverId, { priority: 9 }, data.channelId);
      case 'invalid_group_id':
      case 'invalid_group_pos_id': return;
    }

  };

  var on_fishjoy_5005_cb2 = function(server_id, channel_id, seq_id, err, code){
    if(err) return console.error('[ERROR] %s', err);

    switch(code){
      case 'invalid_user_id':
        return client.send('/queue/front.force.v2.'+ server_id, { priority: 9 }, channel_id);
      case 'invalid_group_id':
      case 'invalid_group_pos_id': return;
    }

    var result = {
      version: 102,
      method: 5008,
      seqId: seq_id,
      timestamp: new Date().getTime(),
      data: JSON.stringify(doc[1])
    };

    var arr = doc[0];

    for(let i=0, j=arr.length; i<j; i++){

      var s = arr[i];

      result.receiver = arr[++i];

      client.send('/queue/back.send.v2.'+ s, { priority: 9 }, JSON.stringify(result));
    }

  };

  /**
   * ready
   */
  var on_fishjoy_5005 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    biz.fishjoy.ready(data.serverId,
      data.channelId,
      on_fishjoy_5005_cb1.bind(null, data.serverId, data.channelId, data.seqId),
      on_fishjoy_5005_cb2.bind(null, data.serverId, data.channelId, data.seqId));
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

  client.connect(headers, onCb, onErr);
})();