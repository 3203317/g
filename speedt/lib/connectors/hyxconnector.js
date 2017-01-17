/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const net = require('net');
const tls = require('tls');
const util = require('util');
const EventEmitter = require('events');

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

(pro => {
  var curId = 1;

  var genSocket = function(socket){
    var hyxsocket = new HyxSocket(curId++, socket);
    this.emit('connection', hyxsocket);
  };

  pro.start = function(cb){
    var self = this;
    console.log('hyxconnector start.');

    if(self.ssl){
      self.tcpServer = tls.createServer(self.ssl);
    }else{
      self.tcpServer = net.createServer();
      self.tcpServer.on('connection', genSocket.bind(self));
    }

    self.tcpServer.listen(self.port);
    setImmediate(cb);
  };
})(pro);

pro.stop = function(force, cb){
  var self = this;
  console.log('hyxconnector stop.');
  self.tcpServer.close();
  setImmediate(cb);
};


const genKey = (rinfo) => rinfo.address +':'+ rinfo.port;