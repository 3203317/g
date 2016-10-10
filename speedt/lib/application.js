/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var path = require('path'),
	fs = require('fs'),
	EventEmitter = require('events').EventEmitter;

var Constants = require('./util/constants'),
	utils = require('./util/utils');

var Application = module.exports = {};

var STATE_INITED  = 1,  // app has inited
    STATE_START   = 2,  // app start
    STATE_STARTED = 3,  // app has started
    STATE_STOPED  = 4;  // app has stoped

Application.init = function(opts){
	var self = this;
	opts = opts || {};

	self.settings = {};                 // collection keep set/get
	self.components = {};               // name -> component map
	self.event = new EventEmitter();    // event object to sub/pub events

	var base = opts.base || path.dirname(require.main.filename);
	self.set(Constants.RESERVED.BASE, base, false);

	// current server info
	self.serverId = null;               // current server id
	self.startTime = null;              // current server start time

	loadDefaultConfiguration.call(self);

	self.state = STATE_INITED;
	console.info('[INFO ] [%s] application inited: %j.'.green, utils.format(), self.serverId);
};

Application.start = function(cb){
	var self = this;
	self.startTime = Date.now();  // current server start time

	if(self.state > STATE_INITED){
		utils.invokeCallback(cb, new Error('application has already start.'));
		return;
	}

	loadDefaultComponents.call(self);
};

Application.stop = function(force){
	var self = this;
};

Application.afterStart = function(cb){
	var self = this;
};

Application.load = function(name, component, opts){
	var self = this;
};

Application.configure = function(env, type, fn){
	fn.call(this);
	return this;
};

Application.before = function(filter){
	addFilter(this, Constants.KEYWORDS.BEFORE_FILTER, filter);
};

Application.filter = function(filter){
	this.before(filter);
	this.after(filter);
};

Application.after = function(filter){
	addFilter(this, Constants.KEYWORDS.AFTER_FILTER, filter);
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

var contains = function(str, settings){
	if(!settings) return false;
	var ss = settings.split('|');
	for(var i in ss){
		if(str === ss[i]) return true;
	}
	return false;
};

var addFilter = function(app, type, filter){
	var filters = app.get(type);
	if(!filters){
		filters = [];
		app.set(type, filters);
	}
	filters.push(filter);
};

/**
 * initialize application configuration.
 */
function loadDefaultConfiguration(){

	var self = this;

	// loadServerConfig
	(() => {
		var originPath = path.join(self.get(Constants.RESERVED.BASE), Constants.FILEPATH.SERVER);
		var serverCfg = require(originPath);
		self.serverId = serverCfg.id;
	})();

	// configLogger
	(() => {
		var originPath = path.join(self.get(Constants.RESERVED.BASE), Constants.FILEPATH.LOG);
		var logCfg = require(originPath);
	})();
}

/**
 * load default components for application.
 */
var loadDefaultComponents = function(){
	var self = this;
	var speedt = require('./speedt');

	self.load(speedt.components.connector, self.get('connectorConfig'));
};