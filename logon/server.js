/*!
 * g-logon
 * Copyright(c) 2016 g-logon <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var colors = require('colors');

var speedt = require('../speedt'),
    utils = require('../speedt/lib/util/utils');

speedt.createApp({ name: 'logonServer' }, function(){
  var self = this;

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
      console.error('[ERROR] [%s] speedt start error: %j.'.red, utils.format(null, 'mm:ss.S'), err.message);
      return;
    }
  });
});

process.on('uncaughtException', (err) => {
  console.error(`caught exception:\n${err.stack}`);
});