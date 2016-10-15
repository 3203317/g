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
  self.logined = {};

  (() => {
    var serverInfo = app.serverInfo;
    var connectionConfig = serverInfo.connection || {};
    self.maxConnections = connectionConfig.maxConnections || opts.maxConnections || 5000;
  })();
};

var pro = Component.prototype;

pro.name = '__connection__';

pro.increaseConnectionCount = function(){
  var self = this;

  if(++self.connCount > self.maxConnections){
    console.warn('[WARN ] [%s] %j has reached the max connections %s'.yellow, utils.format(), self.app.serverId, self.maxConnections);
    self.connCount--;
    return false;
  }

  return true;
};

/**
 * Decrease connection count
 *
 * @param uid {String} uid
 */
pro.decreaseConnectionCount = function(uid){
	this.connCount--;
  if(!!uid) delete this.logined[uid];
};

pro.replaceLoginedUser = function(uid, info){
  var self = this;
  info = info || {};

  var user = self.logined[uid];

  if(!!user){
    updateUserInfo.call(self, uid, info);
    return;
  }

  info.uid = uid;
  self.logined[uid] = info;
};

var updateUserInfo = function(uid, info){
  var self = this;
};

/**
 * Get statistics info
 *
 * @return {Object} statistics info
 */
pro.getStatisticsInfo = function(){
  var self = this;
  return {
    serverId: self.app.serverId,
    connCount: self.connCount,
    loginedCount: utils.size(self.logined),
    logined: self.logined
  }
};