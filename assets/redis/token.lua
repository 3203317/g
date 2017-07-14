-- huangxin <3203317@qq.com>

local db = KEYS[1];
local code = KEYS[2];
local server_id = KEYS[3];
local channel_id = KEYS[4];

local open_time = ARGV[1];

-- 

redis.call('SELECT', db);

local exist = redis.call('EXISTS', code);

if (1 ~= exist) then return 'invalid_code'; end;

-- 

redis.call('EXPIRE', code, 86400);

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('RENAME', code, 'prop::'.. user_id);

redis.call('DEL', client_id ..'::'.. user_id);

-- 

local result = 'OK';

-- 

local s = redis.call('HGET', 'prop::'.. user_id, 'server_id');

if (s) then
  local b = redis.call('HGET', 'prop::'.. user_id, 'channel_id');
  result = s ..'::'.. b;
  redis.call('DEL', result);
end;

-- 

redis.call('HDEL', 'prop::'.. user_id, 'user_id')
redis.call('HMSET', 'prop::'.. user_id, 'server_id', server_id, 'channel_id', channel_id, 'open_time', open_time);

redis.call('SET', server_id ..'::'.. channel_id, user_id);
redis.call('EXPIRE', server_id ..'::'.. channel_id, 86400);

-- 

return result;
