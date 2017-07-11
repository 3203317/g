-- huangxin <3203317@qq.com>

local db = KEYS[1];

local back_id = ARGV[1];

-- 

redis.call('SELECT', 1 + db);

redis.call('DEL', 'prop::back::'.. back_id);

-- 

return 'OK';

