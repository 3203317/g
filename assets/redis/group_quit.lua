-- huangxin <3203317@qq.com>


local key0 = '::';

local server_id = ARGV[1];
local channel_id = ARGV[2];

-- // 根据服务器ID+通道ID获取用户ID

redis.call('SELECT', 6);

local user_id = redis.call('GET', server_id .. key0 .. channel_id);

if (false == user_id) then return 'invalid_channel'; end;



----

redis.call('SELECT', 6);

local group_db = redis.call('HMGET', user_id, 'group_db')[1];

if (false == group_db) then return 'invalid_database'; end;


local group_id = redis.call('HMGET', user_id, 'group_id')[1];
local group_pos_id = redis.call('HMGET', user_id, 'group_pos_id')[1];

redis.call('HDEL', user_id, 'group_id', 'group_pos_id', 'group_db');


----

redis.call('SELECT', 2 + group_db);

redis.call('HDEL', group_id ..'::pos', group_pos_id);

----


redis.call('SELECT', 1 + group_db);

redis.call('MOVE', group_id ..'::'.. group_pos_id, group_db);


----

redis.call('SELECT', 2 + group_db);

-- // 获取当前群组成员（玩家和游客）

local result = redis.call('HGETALL', group_id ..'::pos');


return result;
