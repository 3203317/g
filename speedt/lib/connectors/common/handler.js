/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const ST_INITED = 0,
      ST_WAIT_ACK = 1,
      ST_WORKING = 2,
      ST_CLOSED = 3;

const handlers = {};


var handleHandshake = (socket, pkg) => {
  if(socket.state !== ST_INITED) return;
  socket.emit('heartbeat', pkg);
};

var handleHandshakeAck = (socket, pkg) => {
  if(socket.state !== ST_WAIT_ACK) return;
  socket.emit('heartbeat', pkg);
};

var handleHeartbeat = (socket, pkg) => {
  if(socket.state !== ST_WORKING) return;
  socket.emit('heartbeat', pkg);
};

var handleData = (socket, pkg) => {
  if(socket.state !== ST_WORKING) return;
  socket.emit('message', pkg);
};

var handle = (socket, pkg) => {
  socket.emit('message', pkg);
};

module.exports = handle;