/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var utils = require('../util/utils');

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
  self.connector = getConnector(app, opts);

  self.blacklistFun = opts.blacklistFun;

  // component dependencies
  self.session = null;
  self.connection = null;
};

var pro = Component.prototype;

pro.name = '__connector__';

pro.start = function(cb){
  var self = this;

  self.session = self.app.components.__session__;
  self.connection = self.app.components.__connection__;

  // check component dependencies
  if(!self.session){
    process.nextTick(() => {
      utils.invokeCallback(cb, new Error('fail to start connector component for no session component loaded'));
    });
    return;
  }

  if(!self.connection){
    process.nextTick(() => {
      utils.invokeCallback(cb, new Error('fail to start connector component for no connection component loaded'));
    });
    return;
  }

  process.nextTick(cb);
};

pro.afterStart = function(cb){
  this.connector.start(cb);
  this.connector.on('connection', hostFilter.bind(this, bindEvents));
};

pro.stop = function(force, cb){
  if(this.connector){
    this.connector.stop(force, cb);
    this.connector = null;
    return;
  }

  process.nextTick(cb);
};

var getConnector = (app, opts) => {
	var connector = opts.connector;
	if(!connector){
		return getDefaultConnector(app, opts);
	}

	if('function' !== typeof connector){
		return connector;
	}

	var serverInfo = app.serverInfo;
	return connector(serverInfo.connector.port, serverInfo.connector.host, opts);
};

var getDefaultConnector = (app, opts) => {
	return null;
};

var hostFilter = function(bindEvents, socket){
  var self = this;

  if(!!self.blacklistFun && 'function' === typeof self.blacklistFun){

    self.blacklistFun(socket.host, (err, result) => {
      if(err){
        console.error('[ERROR] [%s] connector blacklist error: %j'.red, utils.format(), err.stack);
        socket.disconnect();
        return;
      }

      if(!result){
        socket.disconnect();
        return;
      }

      bindEvents.call(self, socket);
    });
    return;
  }

  bindEvents.call(self, socket);
};

var bindEvents = function(socket){
  var self = this;

  console.log(self.connection.start);

  socket.on('disconnect', () => {

  });

  socket.on('error', () => {

  });

  // new message
  socket.on('message', (msg) => {

  });
};