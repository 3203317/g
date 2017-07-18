-- huangxin <3203317@qq.com>

local db = KEYS[1];
local back_id = KEYS[2];

local open_time = ARGV[1];

-- 

redis.call('SELECT', 1 + db);

redis.call('HSET', 'prop::back::'.. back_id, 'open_time', open_time);

-- 

return 'OK';
