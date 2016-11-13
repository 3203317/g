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

  self.encode = self.connector.encode || opts.encode;
  self.decode = self.connector.decode || opts.decode;
  self.useCrypto = opts.useCrypto;

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

  if(!self.connection.increaseConnectionCount()){
    socket.disconnect(101);
    return;
  }

  (() => {
    //create session for connection
    var session = getSession.call(self, socket);
    var closed = false;

    socket.on('disconnect', () => {
      if(closed) return;
      closed = true;
      self.connection.decreaseConnectionCount();
    });

    socket.on('error', () => {
      if(closed) return;
      closed = true;
      self.connection.decreaseConnectionCount();
    });

    // new message
    socket.on('message', (msg) => {

      var dmsg = self.decode.call(self, msg, session);

      if(!dmsg){
        // discard invalid message
        return;
      }

      // use rsa crypto
      if(self.useCrypto){
        var verified = verifyMessage.call(self, session, dmsg);
        if(!verified){
          logger.error('fail to verify the data received from client.');
          return;
        }
      }

      handleMessage.call(self, session, dmsg);
    });
  })();
};

var getSession = function(socket){
  var self = this;

  var sid = socket.id;
  var session = self.session.get(sid);
  if(session){
    return session;
  }

  session = self.session.create(self.app.serverId, socket);

  socket.on('disconnect', session.closed.bind(session));
  socket.on('error', session.closed.bind(session));

  return session;
};

var verifyMessage = function(session, msg){

};

var handleMessage = function(session, msg){

};