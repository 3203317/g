/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const utils = require('../util/utils');

module.exports = function(app, opts){
  return new Component(app, opts);
};

/**
 *
 */
var Component = function(app, opts){
  var self = this;
  opts = opts || {};
  self.app = app;
  self.encode = opts.encode;
  self.decode = opts.decode;
  self.connector = getConnector(app, opts);
};

var pro = Component.prototype;

pro.name = '__connector__';

pro.start = function(cb){
  var self = this;
  self.connection = self.app.components.__connection__;

  // check component dependencies
  if(!self.connection){
    return setImmediate(() => {
      utils.invokeCallback(cb, new Error('fail to start connector component for no connection component loaded.'));
    });
  }

  console.log('__connector__ start');
  setImmediate(cb);
};

pro.afterStart = function(cb){
  this.connector.start(cb);
  this.connector.on('connection', hostFilter.bind(this, bindEvents));
};

pro.stop = function(force, cb){
  if(!this.connector) return setImmediate(cb);
  this.connector.stop(force, cb);
  this.connector = null;
};

const getConnector = (app, opts) => {
  var connector = opts.connector;
  if(!connector) return getDefaultConnector(app, opts);
  if('function' !== typeof connector) return connector;
  return connector(app.port, app.host, opts);
};

const getDefaultConnector = (app, opts) => {
  var DefaultConnector = require('../connectors/hyxconnector');
  return new DefaultConnector(app.port, app.host, opts);
};

const hostFilter = function(cb, socket){
  cb.call(this, socket);
};

const bindEvents = function(socket){
  var self = this;
  if(!self.connection.increaseConnectionCount()){
    return socket.disconnect();
  }

  console.log(self.connection.getStatisticsInfo());
  console.log('++++');

  socket.on('disconnect', () => {

  });

  socket.on('error', () => {

  });

  socket.on('message', (msg) => {
    handleMessage.call(self, session, msg);
  });
};

const handleMessage = function(session, msg){
  // todo
};