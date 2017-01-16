/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const fs = require('fs'),
      path = require('path');

const SpeedT = module.exports = {
  version: require('../package.json').version,  // Current version
  components: {},
  connectors: {},
  filters: {},
};

const load = (p, name) => require(!name ? p : (p + name));

SpeedT.createApp = function(opts){
  var app = require('./application');
  app.init(opts);
  return app;
};

fs.readdirSync(path.join(__dirname, 'components')).forEach(filename => {
  if(!/\.js$/.test(filename)) return;
  var name = path.basename(filename, '.js');
  Object.defineProperty(SpeedT.components, name, {
    get: load.bind(null, './components/', name),
    enumerable: true
  });
});