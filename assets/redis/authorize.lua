-- huangxin <3203317@qq.com>

local db        = KEYS[1];
local client_id = KEYS[2];
local user_id   = KEYS[3];

-- 

redis.call('SELECT', db);

local  _key = client_id ..'::'.. user_id;

local code = redis.call('GET', _key);
if (code) then return code; end;

      code         = KEYS[4];

local seconds      = ARGV[1];
local extend_data  = ARGV[2];
local score        = ARGV[3];
local tool_1       = ARGV[4];
local tool_2       = ARGV[5];
local tool_3       = ARGV[6];
local tool_4       = ARGV[7];
local tool_5       = ARGV[8];
local tool_6       = ARGV[9];
local tool_7       = ARGV[10];
local tool_8       = ARGV[11];
local tool_9       = ARGV[12];
local bullet_level = ARGV[13];

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
redis.call('HMSET', code, 'client_id', client_id, 'user_id', user_id, 'extend_data', extend_data);

redis.call('HSET', code, 'score', score);

redis.call('HSET', code, 'tool_1', tool_1);
redis.call('HSET', code, 'tool_2', tool_2);
redis.call('HSET', code, 'tool_3', tool_3);
redis.call('HSET', code, 'tool_4', tool_4);
redis.call('HSET', code, 'tool_5', tool_5);
redis.call('HSET', code, 'tool_6', tool_6);
redis.call('HSET', code, 'tool_7', tool_7);
redis.call('HSET', code, 'tool_8', tool_8);
redis.call('HSET', code, 'tool_9', tool_9);

redis.call('HSET', code, 'bullet_level', bullet_level);

redis.call('EXPIRE', code, seconds);

return code;
