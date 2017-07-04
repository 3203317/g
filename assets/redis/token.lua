
-- huangxin <3203317@qq.com>

redis.call('SELECT', 1);

local code = ARGV[1];

local exist = redis.call('EXISTS', code);

if 1 ~= exist then return '401'; end;

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('DEL', client_id ..'::'.. user_id);
redis.call('DEL', code);

-- 

local server_id = ARGV[2];
local channel_id = ARGV[3];
-- local seconds = ARGV[4];

-- 

local result = 'OK';

local group_id = redis.call('HMGET', user_id, 'group_id')[1];

if (group_id) then
  local s = redis.call('HMGET', user_id, 'server_id')[1];
  local b = redis.call('HMGET', user_id, 'channel_id')[1];
  result = s ..'::'.. b;
  redis.call('DEL', result);
end;

-- 

redis.call('HMSET', user_id, 'client_id', client_id, KEYS[2], server_id, KEYS[3], channel_id, 'scope', '');
-- redis.call('EXPIRE', user_id, seconds);

local _key = server_id ..'::'.. channel_id;

redis.call('SET', _key, user_id);
-- redis.call('EXPIRE', _key, seconds);

return result;