/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const utils = require('../util/utils');

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
  self.loginedCount = 0;
  self.logined = {};
  self.maxConnections = opts.maxConnections || 5000;
};

var pro = Component.prototype;

pro.name = '__connection__';

pro.increaseConnectionCount = function(){
  var self = this;

  if(++self.connCount > self.maxConnections){
    console.warn('[WARN ] The server %j has reached the max connections %s.'.yellow, self.app.serverId, self.maxConnections);
    self.connCount--;
    return false;
  }

  return true;
};


/**
 *
 * @param {String} uid
 * @param {Object} info
 */
pro.upsertUser = function(uid, info){
  var self = this;
  var user = self.logined[uid];
  if(!user){
    info.uid = uid;
    self.logined[uid] = info;
    self.loginedCount++;
    return;
  }

  // update user info
  for(let p in info){
    console.log(p);
  }
};


(() => {
  var removeLoginedUser = function(uid){
    if(this.logined[uid]) this.loginedCount--;
    delete this.logined[uid];
  };

  /**
   * Decrease connection count
   *
   * @param uid
   */
  pro.decreaseConnectionCount = function(uid){
    this.connCount--;
    if(uid) removeLoginedUser.call(this, uid);
  };
})();

(() => {
  var info = {};

  /**
   * Get statistics info
   *
   * @return {Object}  statistics info
   */
  pro.getStatisticsInfo = function(){
    info.serverId = this.app.serverId;
    info.connCount = this.connCount;
    info.loginedCount = this.loginedCount;
    return info;
  };
})();