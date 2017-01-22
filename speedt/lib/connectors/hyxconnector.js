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

var Connector = function(port, host, opts){
  var self = this;
  if(!(self instanceof Connector)){
    return new Connector(port, host, opts);
  }
  EventEmitter.call(self);

  self.opts = opts || {};
  self.ssl = opts.ssl;
  self.port = port;
  self.host = host;

  // info
  self.__tcpServer__ = null;
};

util.inherits(Connector, EventEmitter);

module.exports = Connector;

var pro = Connector.prototype;

(() => {
  var curId = 1;

  var genSocket = function(socket){
    var hyxsocket = new HyxSocket(curId++, socket);
    this.emit('connection', hyxsocket);
  };

  pro.start = function(cb){
    var self = this;

    if(self.ssl){
      self.__tcpServer__ = tls.createServer(self.ssl);
    }else{
      self.__tcpServer__ = net.createServer();
      self.__tcpServer__.on('connection', genSocket.bind(self));
    }

    self.__tcpServer__.listen(self.port, self.host);
    setImmediate(cb);
  };
})();

pro.stop = function(force, cb){
  console.info('[INFO ] hyxconnector is stoped.'.green);
  this.__tcpServer__.close(setImmediate.bind(null, cb));
};
