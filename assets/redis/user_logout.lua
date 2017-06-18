
-- huangxin <3203317@qq.com>


redis.call('SELECT', 6);

local channel_id = ARGV[2];
local server_id = ARGV[1];

local _key = server_id ..'::'.. channel_id;

redis.call('DEL', _key);


return 'OK';