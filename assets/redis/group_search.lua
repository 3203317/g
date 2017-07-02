-- huangxin <3203317@qq.com>

redis.call('SELECT', 0);

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];



return 'OK';
