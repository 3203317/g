/*!
 * g-logon
 * Copyright(c) 2016 g-logon <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var colors = require('colors');

var speedt = require('../speedt');

speedt.createApp({}, function(){
  var self = this;

  self.set('name', 'logonServer');

  self.configure('production|development', 'connector', () => {
    self.set('connectorConfig', {
      connector: speedt.connector.udpconnector,
      heartbeat: 3,
      useDict: true,
      useProtobuf: true
    });
  });

  self.start((err) => {
    if(err){
      console.error('[ERROR] [%s] speedt start error: %j.'.red, '12:01', err.message);
      return;
    }
  });
});

process.on('uncaughtException', (err) => {
  console.error(`caught exception:\n${err.stack}`);
});