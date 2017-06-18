
-- huangxin <3203317@qq.com>


redis.call('SELECT', 6);

local channel_id = ARGV[2];
local server_id = ARGV[1];

local _key = server_id ..'::'.. channel_id;
local user_id = redis.call('GET', _key);

redis.call('DEL', _key);
redis.call('DEL', user_id);


return 'OK';