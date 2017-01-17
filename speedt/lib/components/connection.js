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
  self.maxConnections = opts.maxConnections || 5000;
};

var pro = Component.prototype;

pro.name = '__connection__';

pro.increaseConnectionCount = function(){
  var self = this;

  if(++self.connCount > self.maxConnections){
    console.warn('[WARN ] The server %s has reached the max connections %s.'.yellow, self.app.serverId, self.maxConnections);
    self.connCount--;
    return false;
  }

  return true;
};

/**
 * Decrease connection count
 */
pro.decreaseConnectionCount = function(){
  this.connCount--;
};

(pro => {
  var info = {};

  /**
   * Get statistics info
   *
   * @return {Object}  statistics info
   */
  pro.getStatisticsInfo = function(){
    info.serverId = this.app.serverId;
    info.connCount = this.connCount;
    return info;
  };
})(pro);