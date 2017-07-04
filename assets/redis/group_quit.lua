-- huangxin <3203317@qq.com>

----
local key_0 = '::';

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];

----

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id .. key_0 .. channel_id);

if (false == user_id) then return 'invalid_channel'; end;

--

local group_id = redis.call('HMGET', user_id, 'group_id')[1];

if (false == group_id) then return 'invalid_group'; end;

local group_pos_id = redis.call('HMGET', user_id, 'group_pos_id')[1];
local group_type = redis.call('HMGET', user_id, 'group_type')[1];

redis.call('HDEL', user_id, 'group_id', 'group_pos_id', 'group_db');

----

redis.call('SELECT', 2 + db);

redis.call('HDEL', group_type .. key_0 .. group_id ..'::pos', group_pos_id);

----

redis.call('SELECT', 1 + db);

redis.call('SADD', group_type .. key_0 .. 'idle', group_id .. key_0 .. group_pos_id);

----

redis.call('SELECT', 2 + db);

local result = redis.call('HGETALL', group_type .. key_0 .. group_id ..'::pos');

return result;


