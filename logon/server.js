/*!
 * g-logon
 * Copyright(c) 2016 g-logon <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var colors = require('colors');

var speedt = require('../speedt'),
    utils = require('../speedt/lib/util/utils');

process.on('uncaughtException', (err) => {
  console.error(`caught exception:\n${err.stack}`);
});

var app = speedt.createApp();

app.configure('production|development', 'connector', () => {
  app.set('connectorConfig', {
    connector: speedt.connectors.udpconnector,
    heartbeat: 3,
    useDict: true,
    useProtobuf: true,
    blacklistFun: (ip, cb) => {
      console.log(ip);
      cb(null, true);
    }
  });
});

app.configure('production', 'connection', () => {
  app.set('connectionConfig', {
    maxConnections: 20
  });
});

app.start(err => {
  if(err){
    console.error('[ERROR] [%s] %j start error: %j'.red, utils.format(), app.serverId, err.message);
    return;
  }
});