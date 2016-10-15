/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var utils = require('../util/utils');

module.exports = function(app, opts){
  return new Component(app, opts);
};

/**
 *
 */
var Component = function(app, opts){
  var self = this;
  opts = opts || {};

  self.app = app;

  self.connCount = 0;
};

var pro = Component.prototype;

pro.name = '__connection__';

pro.increaseConnectionCount = function(){
	return ++this.connCount;
};

/**
 * Decrease connection count
 *
 * @param uid {String} uid
 */
pro.decreaseConnectionCount = function(uid){
	this.connCount--;
	removeLoginedUser.call(this, uid);
};

pro.afterStart = function(cb){
  process.nextTick(cb);
};

pro.stop = function(force, cb){
};

var removeLoginedUser = function(uid){
	if(uid) delete this.logined[uid];
};