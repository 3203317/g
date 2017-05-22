-- huangxin <3203317@qq.com>

redis.call('SELECT', 1);

local server_id = ARGV[1];

redis.call('DEL', server_id);

return 'OK';
