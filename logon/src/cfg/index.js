/*!
 * emag.cfg
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const path  = require('path');
const cwd   = process.cwd();

const conf  = require(path.join(cwd, 'settings'));

const http  = require('http');

const ajax  = require('speedt-utils').ajax;

var p1 = new Promise((resolve, reject) => {
  ajax(http.request, {
    host: conf.app.resHost,
    port: 80,
    path: '/assets/fish.trail.json',
    method: 'GET',
  }, null, null).then(html => {
    resolve(JSON.parse(html));
  }).catch(reject);
});

var p2 = new Promise((resolve, reject) => {
  ajax(http.request, {
    host: conf.app.resHost,
    port: 80,
    path: '/assets/fish.type.json',
    method: 'GET',
  }, null, null).then(html => {
    resolve(JSON.parse(html));
  }).catch(reject);
});

var p3 = new Promise((resolve, reject) => {
  ajax(http.request, {
    host: conf.app.resHost,
    port: 80,
    path: '/assets/fish.fixed.json',
    method: 'GET',
  }, null, null).then(html => {
    resolve(JSON.parse(html));
  }).catch(reject);
});

Promise.all([p1, p2, p3]).then(values => {
  exports.fishTrail = values[0].data;
  exports.fishType  = values[1].data;
  exports.fishFixed = values[2].data;
}).catch(function (err){
  process.exit(1);
});

exports.arrayToObject = function(arr){
  let obj = {};

  for(let i=0, j=arr.length; i<j; i++){
    obj[arr[i]] = arr[++i];
  }

  return obj;
};