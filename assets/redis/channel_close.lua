-- huangxin <3203317@qq.com>

redis.call('SELECT', 2);

local server_id = ARGV[1];
local channel_id = ARGV[2];

local exist = redis.call('EXISTS', server_id ..':'.. channel_id);
if 1 ~= exist then return 'OK'; end;

redis.call('SELECT', 1);

redis.call('DECR', server_id ..':connCount');

return 'OK';
