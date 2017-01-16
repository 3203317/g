/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const speedt = require('../../speedt')

process.on('uncaughtException', err => {
  console.error(`Caught exception:\n${err.stack}`);
});

process.on('exit', code => {
  if(0 === code) return console.log('process exit.');
  console.error('process exit with code: %s.', code);
});

const app = speedt.createApp({});

app.configure('production|development', () => {
  app.set('connectorConfig', {
    heartbeat: 3,
    useDict: true,
    useProtobuf: true
  });
});

app.start(err => {
  if(err) return console.error('app start error: %s.', err.message);
  console.log('app started.');
});