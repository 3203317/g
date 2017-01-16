/*!
 * speedt
 * Copyright(c) 2016 speedt <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const os = require('os');
const util = require('util');
const exec = require('child_process').exec;

const utils = module.exports;

/**
 * Invoke callback with check
 */
utils.invokeCallback = function(cb){
  if(!!cb && 'function' === typeof cb){
    cb.apply(null, Array.prototype.slice.call(arguments, 1));
  }
};

/**
 * 格式化日期
 *
 * @param {String} format 'YY/MM/dd hh:mm:ss.S'
 * @return
 */
utils.format = function(date, format){
  date = date || new Date;
  format = format || 'hh:mm:ss.S';
  var o = {
    'Y+': date.getYear(),
    'M+': date.getMonth() + 1,                           // month
    'd+': date.getDate(),                                // day
    'h+': date.getHours(),                               // hour
    'm+': date.getMinutes(),                             // minute
    's+': date.getSeconds(),                             // second
    'q+': Math.floor((date.getMonth() + 3) / 3),         // quarter
    'S':  this.padRight(date.getMilliseconds(), '0', 3)  // millisecond
  }

  if(/(y+)/.test(format)){
    format = format.replace(RegExp.$1, (date.getFullYear() +'').substr(4 - RegExp.$1.length));
  }

  for(let k in o){
    if(new RegExp('('+ k +')').test(format)){
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00'+ o[k]).substr((''+ o[k]).length));
    }
  }

  return format;
};

utils.padRight = (str, char, len) => (str + Array(len).join(char)).slice(0, len);

/**
 * Get the count of elements of object
 */
utils.size = function(obj){
  var count = 0;
  for(let i in obj){
    if(obj.hasOwnProperty(i) && 'function' !== typeof obj[i]){
      count++;
    }
  }
  return count;
};