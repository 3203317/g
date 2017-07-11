-- huangxin <3203317@qq.com>

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];

-- 

redis.call('SELECT', db);

local sb = redis.call('HGET', server_id ..'::'.. channel_id, 'user_id');

if (sb) then
  redis.call('DEL', 'prop::'.. sb, server_id ..'::'.. channel_id);
end;

return 'OK';

