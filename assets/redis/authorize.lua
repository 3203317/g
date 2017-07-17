-- huangxin <3203317@qq.com>

local db        = KEYS[1];
local client_id = KEYS[2];
local user_id   = KEYS[3];

-- 

redis.call('SELECT', db);

local  _key = client_id ..'::'.. user_id;

local code = redis.call('GET', _key);
if (code) then return code; end;

      code          = KEYS[4];

local seconds       = ARGV[1];
local extend_data   = ARGV[2];

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
redis.call('EXPIRE', code, seconds);

return code;
