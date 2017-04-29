
-- huangxin <3203317@qq.com>

redis.call('SELECT', 2);

local exist = redis.call('EXISTS', ARGV[1]);

if 1 ~= exist then return '40001'; end;

-- local code = redis.call('HGETALL', ARGV[1]);
-- redis.call('DEL', code[2] .. code[6]);

local client_id = redis.call('HGET', ARGV[1], 'client_id');
local user_id = redis.call('HGET', ARGV[1], 'user_id');

redis.call('DEL', client_id .. user_id);
redis.call('DEL', ARGV[1]);



redis.call('SELECT', 3);


-- access_token = user_id:client_id
local access_token = redis.call('GET', user_id .. client_id);
if access_token then
  redis.call('DEL', user_id .. client_id);
  redis.call('DEL', access_token);
end;

-- user_id:client_id:access_token
redis.call('SET', user_id .. client_id, ARGV[2]);
redis.call('EXPIRE', user_id .. client_id, ARGV[4]);

--[[
{
  access_token: {
    client_id: '',
    user_id: '',
    scope: ''
  }
}
--]]
redis.call('HMSET', ARGV[2], 'client_id', client_id, 'user_id', user_id, 'scope', '');
-- access_token: 3600(s)
redis.call('EXPIRE', ARGV[2], ARGV[4]);


return 'OK';