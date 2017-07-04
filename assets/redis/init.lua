-- huangxin <3203317@qq.com>

redis.call('FLUSHALL');

local key_0 = '::';

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];


----

redis.call('SELECT', db);

-- redis.call('SELECT', 0);
-- redis.call('HMSET', 'fishjoy::group_type::1', 'total_players', '4', 'allow_view', 1, 'group_db', 21);

-- redis.call('SELECT', 21);
-- redis.call('SET', 'group_id::3', 'nil');

redis.call('SET', 'a::b', 'haha');

----

redis.call('SELECT', 1 + db);

redis.call('SET', 'qingtong' .. key_0 .. 'total_players', 4);
redis.call('SET', 'baiyin' .. key_0 .. 'total_players', 5);
redis.call('SET', 'huangjin' .. key_0 .. 'total_players', 6);


-- redis.call('SADD', 'baiyin' .. key_0 .. 'idle', 'baiyin_1::1', 'baiyin_1::2', 'baiyin_1::3');
-- redis.call('SADD', 'huangjin' .. key_0 .. 'idle', 'huangjin_1::1', 'huangjin_1::2', 'huangjin_1::3', 'huangjin_1::4');

----


return 'OK';
