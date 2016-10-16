/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var util = require('util');
var EventEmitter = require('events').EventEmitter;

var ST_INITED = 0,
    ST_WAIT_ACK = 1,
    ST_WORKING = 2,
    ST_CLOSED = 3;

var Socket = function(id, server, rinfo){
  var self = this;

  self.id = id;
  self.server = server;
  self.rinfo = rinfo;

  self.host = rinfo.address;
  self.port = rinfo.port;

  EventEmitter.call(self);
  self.state = ST_INITED;
};

util.inherits(Socket, EventEmitter);

module.exports = Socket;

var pro = Socket.prototype;

/**
 * Send byte data package to client.
 *
 * @param {Buffer} msg byte data
 */
pro.send = function(msg){
  if(ST_WORKING !== this.state){
    return;
  }
};

pro.disconnect = function(code){
  if(this.state === ST_CLOSED){
    return;
  }
  this.state = ST_CLOSED;
  this.emit('disconnect', code);
};