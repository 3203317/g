/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var fs = require('fs'),
	path = require('path');

var application = require('./application');

var SpeedT = module.exports = {
	version: require('../package.json').version,	// Current version
	component: {},
	filter: {},
	connector: {}
};

/**
 * connector
 */
SpeedT.connector = {};
SpeedT.connector.__defineGetter__('udpconnector', load.bind(null, './connectors/udpconnector'));

var self = this;

SpeedT.createApp = function(opts, cb){
	var app = application;
	app.init(opts);
	self.app = app;
	cb.bind(app)();
};

Object.defineProperty(SpeedT, 'app', {
	get: function(){ return self.app; }
});

function load(path, name){
	if(name) return require(path + name);
	return require(path);
};