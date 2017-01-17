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

const Constants = require('./util/constants'),
      utils = require('./util/utils'),
      events = require('./util/events');

const STATE_INITED  = 1,  // app has inited
      STATE_START   = 2,  // app start
      STATE_STARTED = 3,  // app has started
      STATE_STOPED  = 4;  // app has stoped

Application.init = function(opts){
  var self = this;
  opts = opts || {};
  self.settings = {};               // collection keep set/get
  self.loaded = [];
  self.components = {};             // name -> component map
  self.event = new EventEmitter();  // event object to sub/pub events

  // current server info
  self.startTime = null;
  self.serverId = null;

  defaultConfiguration.call(self);
  self.state = STATE_INITED;
  console.info('[INFO ] Application inited: %j.', self.getServerId());
};

Application.start = function(cb){
  var self = this;
  self.startTime = new Date();
  if(self.state > STATE_INITED){
    return utils.invokeCallback(cb, new Error('application has already start.'));
  }

  loadDefaultComponents.call(self);

  optComponents(self.loaded, Constants.RESERVED.START, err => {
    if(err) return utils.invokeCallback(cb, err);
    self.state = STATE_START;
    self.afterStart(cb);
  });
};

Application.stop = function(force){
  var self = this;
  if(self.state > STATE_STARTED){
    return utils.invokeCallback(cb, new Error('application is not running now.'));
  }
  self.state = STATE_STOPED;
};

Application.afterStart = function(cb){
  var self = this;
  if(self.state > STATE_STARTED){
    return utils.invokeCallback(cb, new Error('application is not running now.'));
  }

  optComponents(self.loaded, Constants.RESERVED.AFTER_START, err => {
    if(err) return utils.invokeCallback(cb, err);
    self.state = STATE_STARTED;
    var usedTime = Date.now() - self.startTime;
    console.info('[INFO ] %j startup in %s ms.', self.getServerId(), usedTime);
    self.event.emit(events.START_SERVER, self.getServerId());
  });
};


/**
 *
 * @return {Object}  app instance for chain invoke
 */
Application.load = function(name, component, opts){
  var self = this;

  if('string' !== typeof name){
    opts = component;
    component = name;
    name = null;
  }

  if('function' === typeof component){
    component = component(self, opts);
  }

  if(!name && 'string' === typeof component.name){
    name = component.name;
  }

  if(name && self.components[name]){
    console.warn('[WARN ] ignore duplicate component: %j.', name);
    return self;
  }

  self.loaded.push(component);
  self.components[name] = component;
  return self;
};

Application.configure = function(env, type, fn){
  var args = [].slice.call(arguments);
  fn = args.pop();

  if(0 < args.length) env = args[0];
  if(1 < args.length) type = args[1];

  var self = this;
  fn.call(self);
  return self;
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
  configLogger.call(self);
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

    if(!isNaN(Number(value)) && (0 > value.indexOf('.'))){
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
  self.set(Constants.RESERVED.SERVER_HOST, args.host, true);
  self.set(Constants.RESERVED.SERVER_PORT, args.port, true);
};

const configLogger = function(){
  // todo
};

/**
 * load default components for application
 */
const loadDefaultComponents = function(){
  var speedt = require('../');
  var self = this;
  self.load(speedt.components.connector, self.get('connectorConfig'));
  self.load(speedt.components.monitor, self.get('monitorConfig'));
};


const optComponents = (comps, method, cb) => {
  async.forEachSeries(comps, function (comp, done){
    if('function' === typeof comp[method]){
      return comp[method](done);
    }
    done();
  }, function (err){
    utils.invokeCallback(cb, err);
  })
};