-- huangxin <3203317@qq.com>

redis.call('SELECT', 8);

local client_id = ARGV[3];
local user_id = ARGV[2];

local  _key = client_id ..'::'.. user_id;

local code = redis.call('GET', _key);
if code then return code; end;

code = ARGV[1];

local seconds = ARGV[4];


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
redis.call('HMSET', code, KEYS[3], client_id, KEYS[2], user_id);
redis.call('EXPIRE', code, seconds);

return code;


