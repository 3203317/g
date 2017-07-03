-- huangxin <3203317@qq.com>

redis.call('FLUSHALL');

redis.call('SELECT', 0);
redis.call('HMSET', 'fishjoy::group_type::1', 'total_players', '4', 'allow_view', 1, 'db', 21);

-- redis.call('SELECT', 21);
-- redis.call('SET', 'group_id::3', 'nil');

return 'OK';
