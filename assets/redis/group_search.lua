-- huangxin <3203317@qq.com>

redis.replicate_commands();

----//

redis.call('SELECT', 0);

--::
local key1 = KEYS[1];


local server_id = ARGV[1];
local channel_id = ARGV[2];
local game_name = ARGV[3]
local group_type = ARGV[4];


--local database = redis.call('HMGET', 'fishjoy::group_type::1', 'database');
local database = redis.call('HMGET', game_name .. key1 ..'group_type'.. key1 .. group_type, 'database')[1];


if (false == database) then return '40001'; end;


----//

redis.call('SELECT', 6);

local user_id = redis.call('GET', server_id .. key1 .. channel_id);

if (false == user_id) then return '40002'; end;


----//

redis.call('SELECT', database);


local ran_group_pos = redis.call('RANDOMKEY');


if (false == ran_group_pos) then return '40003'; end;


redis.call('SET', ran_group_pos, user_id);


redis.call('MOVE', ran_group_pos, 1 + database);


return ran_group_pos;









