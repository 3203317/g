/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var fs = require('fs'),
	path = require('path');

var SpeedT = module.exports = {
	version: require('../package.json').version,	// Current version
	components: {},
	connectors: {},
	filters: {}
};

SpeedT.connectors.__defineGetter__('udpconnector', load.bind(null, './connectors/udpconnector'));

SpeedT.createApp = function(opts){
	var app = require('./application');
	app.init(opts);
	return app;
};

/**
 * Auto-load bundled components with getters.
 */
fs.readdirSync(__dirname + '/components').forEach(filename => {
	if(!/\.js$/.test(filename)) return;

	var name = path.basename(filename, '.js');
	var _load = load.bind(null, './components/', name);

	SpeedT.components.__defineGetter__(name, _load);
});

fs.readdirSync(__dirname + '/filters/handler').forEach(filename => {
	if(!/\.js$/.test(filename)) return;

	var name = path.basename(filename, '.js');
	var _load = load.bind(null, './filters/handler/', name);

	Object.defineProperty(SpeedT.filters, name, {
		get: _load,
		enumerable: !0
	});
});

function load(path, name){
	if(!!name) return require(path + name);
	return require(path);
};