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

  // component dependencies
  self.__session__ = null;
  self.__connection__ = null;
};

var pro = Component.prototype;

pro.name = '__connector__';

pro.start = function(cb){
  var self = this;
  self.__connection__ = self.app.components.__connection__;
  self.__session__ = self.app.components.__session__;

  // check component dependencies
  if(!self.__session__){
    return setImmediate(utils.invokeCallback.bind(null, cb, new Error('fail to start session component for no session component loaded')));
  }

  if(!self.__connection__){
    return setImmediate(utils.invokeCallback.bind(null, cb, new Error('fail to start connector component for no connection component loaded')));
  }

  console.info('[INFO ] Connector component is start.'.green);
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

pro.send = function(reqId, route, msg, opts, cb){
  // todo
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
  if(!self.__connection__.increaseConnectionCount()){
    return socket.disconnect();
  }

  // socket event
  (() => {
    var session = getSession.call(self, socket);

    var disconnect = () => {
      socket.removeListener('message', message);
      socket.removeListener('disconnect', disconnect);
      socket.removeListener('error', error);
      self.__connection__.decreaseConnectionCount(session.uid);
    };

    var error = (cb, err) => {
      utils.invokeCallback(null, cb);
      console.error('[ERROR] Socket exception: %j.'.red, err.message);
    };

    var message = (msg) => {
      handleMessage.call(self, session, msg);
    };

    socket.on('disconnect', disconnect);
    socket.on('error', error.bind(null, disconnect));
    socket.on('message', message);
  })();
};

const handleMessage = function(session, msg){
  var self = this;
  console.log(self.__connection__.getStatisticsInfo());
};

const getSession = function(socket){
  var self = this;
  var sid = socket.id;
  var session = self.__session__.get(sid);
  if(session) return session;
  return session;
};