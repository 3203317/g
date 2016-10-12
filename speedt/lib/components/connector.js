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

  // component dependencies
  self.session = null;
};

var pro = Component.prototype;

pro.name = '__connector__';

pro.start = function(cb){
  var self = this;

  self.session = self.app.components.__session__;

  // check component dependencies
  if(!self.session) {
    process.nextTick(() => {
      utils.invokeCallback(cb, new Error('fail to start connector component for no session component loaded'));
    });
    return;
  }

  process.nextTick(cb);
};

pro.afterStart = function(cb){
  this.connector.start(cb);
};

pro.stop = function(force, cb){
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