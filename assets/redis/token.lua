-- huangxin <3203317@qq.com>

local db = KEYS[1];

local code = ARGV[1];
local server_id = ARGV[2];
local channel_id = ARGV[3];
-- local seconds = ARGV[4];
local open_time = ARGV[4];

-- 

redis.call('SELECT', db);

local exist = redis.call('EXISTS', code);

if (1 ~= exist) then return 'invalid_code'; end;

-- 

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');
local score = redis.call('HGET', code, 'score');

local tool_1 = redis.call('HGET', code, 'tool_1');
local tool_2 = redis.call('HGET', code, 'tool_2');
local tool_3 = redis.call('HGET', code, 'tool_3');
local tool_4 = redis.call('HGET', code, 'tool_4');

redis.call('DEL', code, client_id ..'::'.. user_id);

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

redis.call('HMSET', 'prop::'.. user_id, 'client_id', client_id, KEYS[2], server_id, KEYS[3], channel_id, 'scope', '', 'open_time', open_time, 'score', score, 'tool_1', tool_1, 'tool_2', tool_2, 'tool_3', tool_3, 'tool_4', tool_4);
-- redis.call('EXPIRE', 'prop::'.. user_id, seconds);

redis.call('SET', server_id ..'::'.. channel_id, user_id);
-- redis.call('EXPIRE', server_id ..'::'.. channel_id, seconds);

return result;

