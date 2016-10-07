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

var STATE_INITED  = 1,	// app has inited
	STATE_START   = 2,	// app start
	STATE_STARTED = 3,	// app has started
	STATE_STOPED  = 4;	// app has stoped

Application.init = function(opts){
	var self = this;
	self.settings = opts || {};

	self.event = new EventEmitter();	// event object to sub/pub events

	self.state = STATE_INITED;
	console.info('[INFO ] [%s] application inited: %j.'.green, utils.format(), self.get('name'));
};

Application.start = function(cb){
	var self = this;
	self.startTime = Date.now();	// current server start time

	if(self.state > STATE_INITED){
		utils.invokeCallback(cb, new Error('application has already start.'));
		return;
	}

	loadDefaultComponents();
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

Application.set = function(key, val){
	this.settings[key] = val;
	return this;
};

Application.get = function(setting){
	return this.settings[setting];
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

var loadDefaultComponents = function(){
	console.log('loadDefaultComponents')
};