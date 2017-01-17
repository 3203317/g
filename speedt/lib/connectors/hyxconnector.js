/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const net = require('net');
const util = require('util');
const EventEmitter = require('events');

var Connector = function(port, host, opts){
	var self = this;
	if(!(self instanceof Connector)){
		return new Connector(port, host, opts);
	}
 	EventEmitter.call(self);

	self.opts = opts || {};
};

util.inherits(Connector, EventEmitter);

module.exports = Connector;

var pro = Connector.prototype;

pro.start = function(cb){
  var self = this;
  console.log('hyxconnector start.');
  setImmediate(cb);
};

pro.stop = function(force, cb){
  var self = this;
  console.log('hyxconnector stop.');
  setImmediate(cb);
};


const genKey = (rinfo) => rinfo.address +':'+ rinfo.port;