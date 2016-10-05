/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var net = require('net');
var util = require('util');
var dgram = require("dgram");
var EventEmitter = require('events').EventEmitter;

var Connector = function(port, host, opts){

	var self = this;

	if(!(self instanceof Connector)){
		return new Connector(port, host, opts);
	}

 	EventEmitter.call(self);

	self.opts = opts || {};
	self.type = opts.udpType || 'udp4';

	self.clients = {};

	self.port = port;
	self.host = host;
};

util.inherits(Connector, EventEmitter);

module.exports = Connector;

Connector.prototype.start = function(cb){

	var self = this;

	var server = dgram.createSocket(self.type, () => {
		console.log(arguments.length);
	});

	self.server = server;

	server.on('error', (err) => {
		console.log(`server error:\n${err.stack}`);
		server.close();
	});

	server.on('message', (msg, rinfo) => {
		console.log(arguments.length);
	});

	server.on('listening', () => {
		var address = server.address();
		console.log(`server listening ${address.address}:${address.port}`);
	});

	server.bind(self.port);

	process.nextTick(cb);
};

Connector.prototype.stop = function(force, cb){
	this.socket.close();
	process.nextTick(cb);
};

var genKey = function(rinfo) {
  return rinfo.address +':'+ rinfo.port;
};