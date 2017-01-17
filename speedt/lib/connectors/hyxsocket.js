/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const util = require('util');
const EventEmitter = require('events');

const ST_INITED = 0,
      ST_WAIT_ACK = 1,
      ST_WORKING = 2,
      ST_CLOSED = 3;

var Socket = function(id, socket){
  var self = this;

  EventEmitter.call(self);

  self.id = id;
  self.socket = socket;

  self.state = ST_INITED;
};

util.inherits(Socket, EventEmitter);

module.exports = Socket;

var pro = Socket.prototype;

pro.sendRaw = function(msg){};

pro.send = function(msg){};

pro.disconnect = function(msg){
  var self = this;
  if(self.state === ST_CLOSED) return;

  self.state = ST_CLOSED;
  self.socket.emit('close');
  self.socket.close();
};