/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const util = require('util'),
      EventEmitter = require('events');

const handler = require('./common/handler');

const ST_INITED = 0,
      ST_WAIT_ACK = 1,
      ST_WORKING = 2,
      ST_CLOSED = 3;

var Socket = function(id, socket, timeout, noDelay){
  var self = this;
  EventEmitter.call(self);

  self.id = id;
  self.__socket__ = socket;

  self.remoteAddress = {
    ip:   socket.remoteAddress,
    port: socket.remotePort
  };


  socket.setNoDelay(noDelay);

  socket.once('close', self.emit.bind(self, 'disconnect'));
  socket.once('error', self.emit.bind(self, 'error'));

  socket.setTimeout(timeout, self.disconnect.bind(self));

  socket.on('data', msg => {
    if(!msg) return;
    handler(self, msg);
  });

  self.state = ST_INITED;
};

util.inherits(Socket, EventEmitter);

module.exports = Socket;

var pro = Socket.prototype;

// pro.sendRaw = function(msg){
//   var self = this;
//   if(self.state !== ST_WORKING) return;
// };

// pro.send = function(msg){
//   if(msg instanceof String){
//     msg = new Buffer(msg);
//   }else if(!(msg instanceof Buffer)){
//     msg = new Buffer(JSON.stringify(msg));
//   }
//   this.sendRaw(msg);
// };

pro.disconnect = function(){
  var self = this;
  if(self.state === ST_CLOSED) return;
  self.state = ST_CLOSED;
  self.__socket__.destroy();
};