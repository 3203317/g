/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var path = require('path'),
	fs = require('fs'),
	EventEmitter = require('events').EventEmitter;

var Constants = require('./util/constants');

var Application = module.exports = {};

var STATE_INITED  = 1,	// app has inited
	STATE_START   = 2,	// app start
	STATE_STARTED = 3,	// app has started
	STATE_STOPED  = 4;	// app has stoped

Application.init = function(opts){
	var self = this;
	opts = opts || {};
	self.settings = {};
};

Application.start = function(cb){
	var self = this;
	self.startTime = Date.now();
};

Application.afterStart = function(cb){
	var self = this;
};

Application.load = function(name, component, opts){
	var self = this;
};

Application.configure = function(env, type, fn){
	
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