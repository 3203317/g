-- huangxin <3203317@qq.com>

redis.call('FLUSHALL');

local key_0 = '::';

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];


----

redis.call('SELECT', db);

-- redis.call('SET', 'a::b', 'haha');

----

redis.call('SELECT', 1 + db);

-- min_run 最小运行

redis.call('HMSET', 'prop::fishjoy:qingtong', 'total_players', 4, 'total_visitors', 0, 'min_run', 1);
redis.call('HMSET', 'prop::fishjoy:baiyin', 'total_players', 7, 'total_visitors', 0, 'min_run', 1);
redis.call('HMSET', 'prop::fishjoy:huangjin', 'total_players', 6, 'total_visitors', 0, 'min_run', 1);


redis.call('HMSET', 'prop::tuibing:huangjin', 'total_players', 4, 'total_visitors', 6, 'min_run', 4);



-- redis.call('SADD', 'baiyin' .. key_0 .. 'idle', 'baiyin_1::1', 'baiyin_1::2', 'baiyin_1::3');
-- redis.call('SADD', 'huangjin' .. key_0 .. 'idle', 'huangjin_1::1', 'huangjin_1::2', 'huangjin_1::3', 'huangjin_1::4');

----


return 'OK';
