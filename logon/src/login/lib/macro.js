/*!
 * emag.login
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const fs = require('fs'),
      velocity = require('velocityjs'),
      cwd = process.cwd();

const utils = require('speedt-utils').utils;

function format(date, format){
  if(!date) return '';
  date = date || new Date;
  format = format || 'hh:mm:ss.S';
  var o = {
    'Y+': date.getFullYear(),
    'M+':  utils.padLeft(date.getMonth() + 1, '0', 2),    // month
    'd+':  utils.padLeft(date.getDate(), '0', 2),         // day
    'h+':  utils.padLeft(date.getHours(), '0', 2),        // hour
    'm+':  utils.padLeft(date.getMinutes(), '0', 2),      // minute
    's+':  utils.padLeft(date.getSeconds(), '0', 2),      // second
     'S': utils.padRight(date.getMilliseconds(), '0', 3)  // millisecond
  }
  for(var k in o){
    if(new RegExp('('+ k +')').test(format)){
      format = format.replace(RegExp.$1, o[k]);
    }
  }
  return format;
};

module.exports = {
  parse: function(file){
    var tpl = fs.readFileSync(require('path').join(cwd, 'views', file)).toString();
    return this.eval(tpl);
  },
  include: function(file){
    var tpl = fs.readFileSync(require('path').join(cwd, 'views', file)).toString();
    return tpl;
  },
  formatDate: function(t){
    return format(t, 'YY-MM-dd hh:mm:ss');
    // return format(t, 'YY-MM-dd hh:mm:ss.S');
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