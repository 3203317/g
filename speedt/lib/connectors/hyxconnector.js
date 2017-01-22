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
  self.tcpServer = null;
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
      self.tcpServer = tls.createServer(self.ssl);
    }else{
      self.tcpServer = net.createServer();
      self.tcpServer.on('connection', genSocket.bind(self));
    }

    self.tcpServer.listen(self.port, self.host);
    setImmediate(cb);
  };
})();

pro.stop = function(force, cb){
  this.tcpServer.close();
  console.info('[INFO ] hyxconnector is stoped.'.green);
  setImmediate(cb);
};

const genKey = (rinfo) => rinfo.address +':'+ rinfo.port;