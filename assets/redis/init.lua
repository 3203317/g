-- huangxin <3203317@qq.com>

redis.call('FLUSHALL');

local key_0 = '::';

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];


----

redis.call('SELECT', db);

redis.call('SET', 'a::b', 'haha');

----

redis.call('SELECT', 1 + db);

redis.call('HMSET', 'group::qingtong', 'total_users', 4);
redis.call('HMSET', 'group::baiyin', 'total_users', 7, 'is_allow_visit', 1);
redis.call('HMSET', 'group::huangjin', 'total_users', 6);



-- redis.call('SADD', 'baiyin' .. key_0 .. 'idle', 'baiyin_1::1', 'baiyin_1::2', 'baiyin_1::3');
-- redis.call('SADD', 'huangjin' .. key_0 .. 'idle', 'huangjin_1::1', 'huangjin_1::2', 'huangjin_1::3', 'huangjin_1::4');

----


return 'OK';
