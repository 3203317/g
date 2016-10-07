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
	filter: {}
};

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

/**
 * Auto-load bundled components with getters.
 */
fs.readdirSync(__dirname + '/components').forEach(filename => {
	if(!/\.js$/.test(filename)){
		return;
	}

	var name = path.basename(filename, '.js');
	var _load = load.bind(null, './components/', name);

	SpeedT.component.__defineGetter__(name, _load);
});

function load(path, name){
	if(name) return require(path + name);
	return require(path);
};