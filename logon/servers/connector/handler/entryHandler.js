/*!
 * g-logon
 * Copyright(c) 2016 g-logon <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

var Handler = function(app){
	this.app = app;
};

module.exports = function(app){
	return new Handler(app);
};

var proto = Handler.prototype;

/**
 * New client entry.
 *
 * @param  {Object}   msg     request message
 * @param  {Object}   session current session object
 * @param  {Function} next    next step callback
 * @return {Void}
 */
proto.entry = function(msg, session, next){
	next(null, {
		code: 200,
		msg: 'game server is ok.'
	});
};