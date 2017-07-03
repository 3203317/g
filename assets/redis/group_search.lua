-- huangxin <3203317@qq.com>

redis.replicate_commands();

-- ::
local key1 = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local game_name = ARGV[3]
local group_type = ARGV[4];

local result = nil;

-- // 根据群组类型获取对应的数据库

redis.call('SELECT', 0);

-- local db = redis.call('HMGET', 'fishjoy::group_type::1', 'db');
local db = redis.call('HMGET', game_name .. key1 ..'group_type'.. key1 .. group_type, 'db')[1];

if (false == db) then return '40001'; end;

-- // 根据服务器ID+通道ID获取用户ID

redis.call('SELECT', 6);

local user_id = redis.call('GET', server_id .. key1 .. channel_id);

if (false == user_id) then return 'invalid_channel'; end;

-- // 随机获取一个群组中的座位并占用该座位，然后将该座位移动到另一个库

redis.call('SELECT', db);

local ran_group_pos = redis.call('RANDOMKEY');

if (false == ran_group_pos) then return '40003'; end;

redis.call('MOVE', ran_group_pos, 1 + db);


----

redis.call('SELECT', 1 + db);




result = redis.call('SET', ran_group_pos, user_id);



----

redis.call('SELECT', 6);


local group_id, group_pos_id = string.match(ran_group_pos, "(.*)%::(.*)");


redis.call('HMSET', user_id, 'group_id', group_id, 'group_pos_id', group_pos_id);


----


redis.call('SELECT', 1 + db);








return result;









