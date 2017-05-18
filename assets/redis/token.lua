
-- huangxin <3203317@qq.com>

redis.call('SELECT', 8);

local code = ARGV[1];

local exist = redis.call('EXISTS', code);

if 1 ~= exist then return '401'; end;

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('DEL', client_id .. user_id);
redis.call('DEL', code);



redis.call('SELECT', 6);


local access_token = redis.call('GET', user_id .. client_id);
if access_token then
  redis.call('DEL', user_id .. client_id);
  redis.call('DEL', access_token);
end;


access_token = ARGV[2];
local seconds = ARGV[3];

redis.call('SET', user_id .. client_id, access_token);
redis.call('EXPIRE', user_id .. client_id, seconds);

--[[
{
  access_token: {
    client_id: '',
    user_id: '',
    scope: ''
  }
}
--]]
redis.call('HMSET', access_token, 'client_id', client_id, 'user_id', user_id, 'scope', '');
-- access_token: 3600(s)
redis.call('EXPIRE', access_token, seconds);


return 'OK';