/*!
 * emag.model
 * Copyright(c) 2016 huangxin <3203317@qq.com>
 * MIT Licensed
 */
'use strict';

module.exports = function(opts){
  return new Method(opts);
}

var Method = function(opts){
  this.init(opts);
  this.fishes = [];
};

var pro = Method.prototype;

pro.init = function(opts){
  var self = this;
  opts = opts || {};
  self.id = opts.id;
  self.capacity = opts.capacity;
  return self;
};

var cfg_fish = [{
	type: 1,
	probability: 0.1,
	weight: 10
},{
	type: 2,
	probability: 0.5,
	weight: 5
}];

pro.getFishes = function(){

  // console.log(this.fishes)

  var s = UpdateFish(this.fishes, cfg_fish, this);

  //
  if(s) this.fishes.push(s);

  // console.log(fishes);

  for(let i of this.fishes){

  	// loop 还没做，如果为false 要从数组中删除

  	if(i.step == 200){
  		i.step = 0;
  	}
  	else{
  		i.step++;
  	}
  }

  return this.fishes;
}

function CreatUUID(i){
	return new Date().getTime();
}

function UpdateFish(fishs, cfg_fish, cfg_pool){	
	var aw=0;
	//累加鱼池中鱼的总重
	for(let f of fishs){
		aw += f.weight; 
	}

	// console.log(cfg_pool)
	// console.log(aw)

	// console.log(aw)

	//超出鱼池可容纳重量 不再创建新鱼
	if(aw >= cfg_pool.capacity) return;

	var new_fish = {
		id: CreatUUID(1),
		type: 0,
		path: 0,
		step: 0,
		max: 222,
		loop: true,
		weight: 15
	};

	//产生随机数
	var r = Math.random();	

	//在配置表中查找 随机数对应的鱼
	for( let f of cfg_fish){
		if(r < f.probability){
			new_fish.type = f.type;
			new_fish.path = Math.round(Math.random()*4);
			break;
		}
	}	

	return new_fish;
}
