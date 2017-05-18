-- huangxin <3203317@qq.com>

redis.call('SELECT', 8);

local client_id = ARGV[3];
local user_id = ARGV[2];

-- code = client_id:user_id
local code = redis.call('GET', client_id .. user_id);
if code then return code; end;

code = ARGV[1];

local seconds = ARGV[4];

-- code = client_id:user_id
redis.call('SET', client_id .. user_id, code);
redis.call('EXPIRE', client_id .. user_id, seconds);

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


