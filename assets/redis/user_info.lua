-- huangxin <3203317@qq.com>

local db      = KEYS[1];
local user_id = KEYS[2];

-- 

redis.call('SELECT', db);

local result = redis.call('HGETALL', 'prop::'.. user_id);

return result;
