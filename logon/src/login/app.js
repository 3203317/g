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

(function(){
  var activemq = conf.emag.activemq;
  var Stomp = require('stomp-client');
  var client = new Stomp(activemq.host, activemq.port, activemq.user, activemq.password);

  function close(){
    if(!client) return;

    client.disconnect(() => {
      console.log('disconnect');
    });
  }

  process.on('uncaughtException', e => {
    close();
  });

  process.on('exit', () => {
    close();
  });

  client.connect(sessionId => {
    console.info('[INFO ] amq client on %s.', sessionId);

    client.subscribe('/queue/front.start', (body, headers) => {
      console.log(body);
      console.log(headers)
    });

    client.subscribe('/queue/front.stop', (body, headers) => {
      console.log(body);
      console.log(headers)
    });

    client.subscribe('/queue/channel.open', (body, headers) => {
      console.log(body);
      console.log(headers)
    });

    client.subscribe('/queue/channel.close', (body, headers) => {
      console.log(body);
      console.log(headers)
    });

  });

})();