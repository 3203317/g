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

// (function(){
//   var activemq = conf.emag.activemq;
//   var Stomp = require('stomp-client');
//   var client = new Stomp(activemq.host, activemq.port, activemq.user, activemq.password);

//   function close(){
//     if(!client) return;

//     client.disconnect(() => {
//       console.log('disconnect');
//     });
//   }

//   client.connect(sessionId => {
//     console.info('[INFO ] amq client on %s.', sessionId);

//     client.subscribe('/queue/front.start', (body, headers) => {
//       console.log(body);
//       console.log(headers)
//     });

//     client.subscribe('/queue/front.stop', (body, headers) => {
//       console.log(body);
//       console.log(headers)
//     });

//     client.subscribe('/queue/channel.open', (body, headers) => {
//       console.log(body);
//       console.log(headers)
//     });

//     client.subscribe('/queue/channel.close', (body, headers) => {
//       console.log(body);
//       console.log(headers)
//     });

//     client.subscribe('/queue/channel.send', (body, headers) => {
//       console.log(body);
//       console.log(headers);
//     });

//   });

// })();


(function(){
  const biz = require('emag.biz');

  var activemq = conf.emag.activemq;

  var Stomp = require('stompjs');
  var client = Stomp.overTCP(activemq.host, activemq.port);

  var err = function(error){
    console.error(error);

    _unsubscribe();
  };

  var _front_start;
  var _front_stop;

  var _channel_open;
  var _channel_close;

  var _2001;

  var _3001;
  var _3005;

  var _fishjoy_5001, _fishjoy_5003, _fishjoy_5005;

  var cb = function(frame){
    _front_start = client.subscribe('/queue/front.start', on_front_start);
    _front_stop = client.subscribe('/queue/front.stop', on_front_stop);

    _channel_open = client.subscribe('/queue/channel.open', on_channel_open);
    _channel_close = client.subscribe('/queue/channel.close', on_channel_close);

    _2001 = client.subscribe('/queue/qq3203317.2001', on_2001);

    _3001 = client.subscribe('/queue/qq3203317.3001', on_3001);
    _3005 = client.subscribe('/queue/qq3203317.3005', on_3005);

    _fishjoy_5001 = client.subscribe('/queue/qq3203317.5001', on_fishjoy_5001);
    _fishjoy_5003 = client.subscribe('/queue/qq3203317.5003', on_fishjoy_5003);
    _fishjoy_5005 = client.subscribe('/queue/qq3203317.5005', on_fishjoy_5005);
  };

  function _unsubscribe(){
    _front_start.unsubscribe();
    _front_stop.unsubscribe();

    _channel_open.unsubscribe();
    _channel_close.unsubscribe();

    _2001.unsubscribe();

    _3001.unsubscribe();
    _3005.unsubscribe();

    _fishjoy_5001.unsubscribe();
    _fishjoy_5003.unsubscribe();
    _fishjoy_5005.unsubscribe();
  }

  process.on('uncaughtException', err => {
    _unsubscribe();
  });

  process.on('exit', () => {
    _unsubscribe();
  });

  var headers = {
    login: activemq.user,
    passcode: activemq.password,
  };

  // 

  var on_front_start = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');
    console.info('[INFO ] front amq start: %s', msg.body);
  };

  var on_front_stop = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');
    console.info('[INFO ] front amq stop: %s', msg.body);
  };

  // 

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

  // 

  var on_2001 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    console.log('[INFO ] chat 1v1 send: %j', data);

    data.method = 2002;
    data.receiver = data.channelId;

    client.send('/queue/back.send.v2.'+ data.serverId, { priority: 9 }, JSON.stringify(data));
  };

  // 

  var on_3001 = function(msg){
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

  // 

  /**
   * shot
   */
  var on_fishjoy_5001 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

  };

  /**
   * blast
   */
  var on_fishjoy_5003 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

  };

  /**
   * ready
   */
  var on_fishjoy_5005 = function(msg){
    if(!msg.body) return console.error('[ERROR] empty message');

    var data = JSON.parse(msg.body);

    biz.fishjoy.ready(data.serverId, data.channelId, function (err, code, doc){
      if(err) return console.error('[ERROR] %s', err);

      switch(code){
        case 'invalid_user_id':
          return client.send('/queue/front.force.v2.'+ data.serverId, { priority: 9 }, data.channelId);
        case 'invalid_group_id':
        case 'invalid_group_pos_id': return;
      }

      if(!doc) return;

      var result = {
        version: 102,
        method: 5006,
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

  };

  // 

  client.connect(headers, cb, err);
})();