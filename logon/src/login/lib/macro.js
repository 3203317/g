/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const fs = require('fs'),
      velocity = require('velocityjs'),
      cwd = process.cwd();

module.exports = {
  parse: function(file){
    var tpl = fs.readFileSync(require('path').join(cwd, 'views', file)).toString();
    return this.eval(tpl);
  },
  include: function(file){
    var tpl = fs.readFileSync(require('path').join(cwd, 'views', file)).toString();
    return tpl;
  },
  toHtml: s => {
    return velocity.render(s);
  },
  toSex: n => {
    switch(n){
      case 1: return '男';
      case 2: return '女';
      default: return '未知';
    }
  },
  indexOf: (s, b) => {
    if(!s) return false;
    if(!b) return false;
    return -1 < s.indexOf(b);
  }
};