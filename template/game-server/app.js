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
  if(0 === code) return;
});

const app = speedt.createApp({});

app.configure('production|development', () => {
  // todo
});

app.start(err => {
  if(err) return;
  console.log('start');
});