/*!
 * g-logon
 * Copyright(c) 2016 g-logon <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var app = require('../speedt/index');

/**
 * Init app for client.
 */
app.createApp(null, function(){
  var self = this;

  // app configuration
  self.configure('production|development', 'connector', () => {
    self.set('connectorConfig', {
      connector : speedt.connector.udpconnector,
      heartbeat : 3,
      useDict : true,
      useProtobuf : true
    });
  });

  self.start((err) => {
    if(err){
      console.error('[ERROR] [%s] app start error: %j.'.red, '12:01', err.message);
      return;
    }
  });
});

process.on('uncaughtException', (err) => {
  console.error(`caught exception:\n${err.stack}`);
});