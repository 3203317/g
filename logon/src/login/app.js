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
  console.error(err);
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

  var Stomp = require('stompjs');
  var client = Stomp.overTCP('127.0.0.1', 12613);

  var err = function(error){
    console.error(error);
  };

  var cb = function(frame){
    client.subscribe('/queue/front.start', on_front_start);
    client.subscribe('/queue/front.stop', on_front_stop);

    client.subscribe('/queue/channel.open', on_channel_open);
    client.subscribe('/queue/channel.close', on_channel_close);

    client.subscribe('/queue/qq3203317.2001', on_2001);
  };

  var headers = {
    login: 'admin',
    passcode: 'admin',
  };

  var on_front_start = function(msg){
    if(!msg.body) return console.error('empty message');
    console.log('front amq start: %s', msg.body);
  };

  var on_front_stop = function(msg){
    if(!msg.body) return console.error('empty message');
    console.log('front amq stop: %s', msg.body);
  };

  // 

  var on_channel_open = function(msg){
    if(!msg.body) return console.error('empty message');

    var s = msg.body.split('::');

    var b = {
      version: 102,
      seqId: 1,
      timestamp: new Date().getTime(),
      receiver: s[1]
    };

    client.send('/queue/back.send.v2.'+ s[0], { priority: 9 }, JSON.stringify(b));
  };

  var on_channel_close = function(msg){
    if(!msg.body) return console.error('empty message');

    console.log(msg.body);
  };

  // 

  var on_2001 = function(msg){
    if(!msg.body) return console.error('empty message');

    var data = JSON.parse(msg.body);

    data.method = 2002;
    data.receiver = data.channelId;

    client.send('/queue/back.send.v2.'+ data.serverId, { priority: 9 }, JSON.stringify(data));
  };

  // 

  client.connect(headers, cb, err);
})();