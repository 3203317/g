-- huangxin <3203317@qq.com>

redis.call('SELECT', 1);

local server_id = ARGV[1];

redis.call('SET', server_id ..':'.. KEYS[2], ARGV[2]);

return 'OK';
