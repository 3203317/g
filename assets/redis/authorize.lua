-- huangxin <3203317@qq.com>

local db = KEYS[1];

local client_id = ARGV[2];
local user_id = ARGV[3];

-- 

redis.call('SELECT', db);

local  _key = client_id ..'::'.. user_id;

local code = redis.call('GET', _key);
if (code) then return code; end;

code = ARGV[1];

local seconds = ARGV[4];
local score = ARGV[5];

local tool_1 = ARGV[6];
local tool_2 = ARGV[7];
local tool_3 = ARGV[8];
local tool_4 = ARGV[9];

redis.call('SET', _key, code);
redis.call('EXPIRE', _key, seconds);

--[[
{
  code: {
    client_id: '',
    user_id: ''
  }
}
--]]
redis.call('HMSET', code, KEYS[2], client_id, KEYS[3], user_id, 'score', score, 'tool_1', tool_1, 'tool_2', tool_2, 'tool_3', tool_3, 'tool_4', tool_4);
redis.call('EXPIRE', code, seconds);

return code;

