/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const net = require('net'),
      tls = require('tls'),
      util = require('util'),
      EventEmitter = require('events');

const HyxSocket = require('./hyxsocket');

const DEFAULT_TIMEOUT = 5;

var Connector = function(port, host, opts){
  var self = this;
  if(!(self instanceof Connector)){
    return new Connector(port, host, opts);
  }
  EventEmitter.call(self);

  opts = opts || {};
  self.ssl = opts.ssl;
  self.port = port;
  self.host = host;

  self.timeout = (opts.timeout || DEFAULT_TIMEOUT) * 1000;
  self.noDelay = !!opts.noDelay;

  // info
  self.__tcpServer__ = null;
};

util.inherits(Connector, EventEmitter);

module.exports = Connector;

var pro = Connector.prototype;

(() => {
  var curId = 1;

  var newSocket = function(socket){
    var hyxsocket = new HyxSocket(curId++, socket, this.timeout, this.noDelay);
    this.emit('connection', hyxsocket);
  };

  pro.start = function(cb){
    var self = this;

    if(self.ssl){
      self.__tcpServer__ = tls.createServer(self.ssl);
    }else{
      self.__tcpServer__ = net.createServer();
      self.__tcpServer__.on('connection', newSocket.bind(self));
    }

    self.__tcpServer__.listen(self.port, self.host);
    setImmediate(cb);
  };
})();

pro.stop = function(force, cb){
  console.info('[INFO ] hyxconnector is stoped.'.green);
  this.__tcpServer__.close(setImmediate.bind(null, cb));
};
