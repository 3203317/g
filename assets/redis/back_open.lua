-- huangxin <3203317@qq.com>

local db = KEYS[1];

local back_id = ARGV[1];
local open_time = ARGV[2];

-- 

redis.call('SELECT', 1 + db);

redis.call('HMSET', 'prop::back::'.. back_id, 'open_time', open_time, 'close_time', 0);

-- 

return 'OK';

