-- huangxin <3203317@qq.com>

redis.call('SELECT', 1);

local server_id = ARGV[1];
local status = ARGV[2];

redis.call('HMSET', server_id, 'connCount', 0, 'status', status);

return 'OK';


