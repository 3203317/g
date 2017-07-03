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

-- local database = redis.call('HMGET', 'fishjoy::group_type::1', 'database');
local database = redis.call('HMGET', game_name .. key1 ..'group_type'.. key1 .. group_type, 'database')[1];

if (false == database) then return '40001'; end;

-- // 根据服务器ID+通道ID获取用户ID

redis.call('SELECT', 6);

local user_id = redis.call('GET', server_id .. key1 .. channel_id);

if (false == user_id) then return '40002'; end;

-- // 随机获取一个群组中的座位并占用该座位，然后将该座位移动到另一个库

redis.call('SELECT', database);

local ran_group_pos = redis.call('RANDOMKEY');

if (false == ran_group_pos) then return '40003'; end;

result = redis.call('SET', ran_group_pos, user_id);

if (false == result) then return '40004'; end;

redis.call('MOVE', ran_group_pos, 1 + database);

return result;









