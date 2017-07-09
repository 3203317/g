/*!
 * emag.biz
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

const EventProxy = require('eventproxy');
const uuid = require('node-uuid');

const utils = require('speedt-utils').utils;

const mysql = require('./emag/mysql');
const redis = require('./emag/redis');

var createNewFishs = function(curr_fish_list, prop_fish_type, prop_group, cb){
  var list = [];
  cb(list);
};

(() => {

  function my_async_function(a){
    a();
  }

  function init(group_info, cb){
    console.log('[INFO ] init');

    var s = group_info.split('::');

    var prop_group = {
      type: s[0],
      id: s[1],
      capacity: s[2]
    };

    var curr_fish_list = [/*{
      id: 'uuid_1',
      type: '鲨鱼',
      weight: 20
    },{
      id: 'uuid_2',
      type: '鳄鱼',
      weight: 15
    }*/];

    var prop_fish_type = [{
      type: '鲨鱼',
      probability: 1,
      weight: 20
    }, {
      type: '鳄鱼',
      probability: 2,
      weight: 15
    }];

    var interval = 1300;

    var i = 0, max = 2000;

    (function schedule(){
      var timeout = setTimeout(function(){

        clearTimeout(timeout);

        my_async_function(function(){

          console.log('async is done!');
          console.log(prop_group)
          console.log('----')
          console.log(timeout);
          schedule();

        });

      }, interval);

    }());

  }

  const numkeys = 2;
  const sha1 = '45ed245f475c604374abccb8dac3b51ffe93af98';

  exports.ready = function(server_id, channel_id, cb){

    redis.evalsha(sha1, numkeys, '1', '123456', server_id, channel_id, (err, doc) => {
      if(err) return cb(err);

      switch(doc){
        case 'invalid_user_id':
        case 'invalid_group_id':
        case 'invalid_group_pos_id': return cb(null, doc);
        case 'back_running': return cb();
        default: init(doc, cb);
      }
    });
  };
})();


