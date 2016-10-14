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

var utils = require('../util/utils');
var UdpSocket = require('./udpsocket');

var curId = 1;

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

var pro = Connector.prototype;

pro.start = function(cb){

	var self = this;

	var server = self.server = dgram.createSocket(self.type, (msg, rinfo) => {
		var key = genKey(rinfo);

		if(!self.clients[key]){

			var udpsocket = new UdpSocket(curId++, self.server, rinfo);
			self.clients[key] = udpsocket;

			udpsocket.on('disconnect', () => {
				delete self.clients[genKey(udpsocket.rinfo)];
			});

			self.emit('connection', udpsocket);
		}
	});

	server.on('error', (err) => {
		console.error('[ERROR] [%s] udp server encounters with error: %j'.red, utils.format(), err.stack);
		return;
	});

	server.on('message', (msg, rinfo) => {
		var server = self.clients[genKey(rinfo)];
		if(!!server){
			server.emit('package', msg);
		}
	});

	server.on('listening', () => {
		var rinfo = server.address();
		console.info('[INFO ] [%s] udp server listening %s:%s'.green, utils.format(), rinfo.address, rinfo.port);
	});

	server.bind(self.port, self.host);

	process.nextTick(cb);
};

pro.stop = function(force, cb){
	this.server.close();
	process.nextTick(cb);
};

var genKey = (rinfo) => {
	return rinfo.address +':'+ rinfo.port;
};