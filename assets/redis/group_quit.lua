-- huangxin <3203317@qq.com>

-- 

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'OK'; end;

local group_pos_id = redis.call('HGET', 'prop::'.. user_id, 'group_pos_id');

redis.call('HDEL', 'prop::'.. user_id, 'group_id', 'group_pos_id');

-- 

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::'.. group_id, 'type');

-- 

local sb = redis.call('HGET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);

if (sb ~= user_id) then return 'OK'; end;

-- 

redis.call('HDEL', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);

redis.call('SADD', 'idle::'.. group_type, group_id ..'::'.. group_pos_id);

-- 

local result = redis.call('HGETALL', 'pos::'.. group_type ..'::'.. group_id);

return result;



