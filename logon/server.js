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

app.set('name', 'logonServer');

app.configure('production|development', 'connector', () => {
  app.set('connectorConfig', {
    connector: speedt.connector.udpconnector,
    heartbeat: 3,
    useDict: true,
    useProtobuf: true
  });
});

app.start(err => {
  if(err){
    console.error('[ERROR] [%s] speedt start error: %j.'.red, utils.format(), err.message);
    return;
  }
});