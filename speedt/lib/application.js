/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const path = require('path'),
      fs = require('fs'),
      async = require('async'),
      EventEmitter = require('events');

const Application = module.exports = {};

const Constants = require('./util/constants');

const STATE_INITED  = 1,  // app has inited
      STATE_START   = 2,  // app start
      STATE_STARTED = 3,  // app has started
      STATE_STOPED  = 4;  // app has stoped

Application.init = function(opts){
  var self = this;
  opts = opts || {};
  self.settings = {};                 // collection keep set/get
  self.components = {};               // name -> component map
  self.event = new EventEmitter();    // event object to sub/pub events

  defaultConfiguration.call(self);
  self.state = STATE_INITED;
  console.info('[INFO ] Application inited: %j.', self.getServerId());
};

Application.start = function(cb){
  var self = this;
};

Application.stop = function(force){
  var self = this;
};

Application.afterStart = function(cb){
  var self = this;
};

Application.configure = function(env, type, fn){
  fn.call(this);
  return this;
};

Application.before = function(filter){
  addFilter.call(this, Constants.KEYWORDS.BEFORE_FILTER, filter);
};

Application.filter = function(filter){
  this.before(filter);
  this.after(filter);
};

Application.after = function(filter){
  addFilter.call(this, Constants.KEYWORDS.AFTER_FILTER, filter);
};


const addFilter = function(type, filter){
  var filters = this.get(type);
  if(!filters){
    filters = [];
    this.set(type, filters);
  }
  filters.push(filter);
};


Application.getServerId = function(){
  return this.serverId;
};

/**
 *
 * @param {String} key
 * @param {String} val
 * @param {Boolean} attach whether attach the settings to application
 */
Application.set = function(key, val, attach){
  this.settings[key] = val;
  if(attach) this[key] = val;
  return this;
};

Application.get = function(key){
  return this.settings[key];
};


const defaultConfiguration = function(){
  var self = this;
  var args = parseArgs(process.argv);
  setupEnv.call(self, args);
  processArgs.call(self, args);
};

const parseArgs = args => {
  var pos = 1;
  var argsMap = { main: args[pos] };

  for(let i=++pos, j=args.length; i<j; i++){
    let arg = args[i];
    let sep = arg.indexOf('=');
    if(-1 === sep) continue;

    let key = arg.slice(0, sep);
    if(!key) continue;

    let value = arg.slice(++sep);
    if(!value) continue;

    if(!isNaN(Number(value)) && 0 > (value.indexOf('.'))){
      value = Number(value);
    }

    argsMap[key] = value;
  }

  return argsMap;
};

const setupEnv = function(args){
  args = args || {};
  this.set(Constants.RESERVED.ENV, args.env || process.env.NODE_ENV || Constants.RESERVED.ENV_DEV);
};

const processArgs = function(args){
  var self = this;
  self.set(Constants.RESERVED.SERVER_ID, args.id, true);
};