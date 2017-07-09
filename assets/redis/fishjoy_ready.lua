-- huangxin <3203317@qq.com>

-- 

local db = KEYS[1];
local back_id = KEYS[2];

local server_id = ARGV[1];
local channel_id = ARGV[2];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 不在任何群组

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 

local group_pos_id = redis.call('HGET', 'prop::'.. user_id, 'group_pos_id');

-- 获取群组的类型

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::'.. group_id, 'type');

-- 

local s = redis.call('HGET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'invalid_group_pos_id'; end;

-- 判断是否是本人

local b, hand = string.match(s, '(.*)%::(.*)');

if (b ~= user_id) then return 'invalid_user_id'; end;

-- 

redis.call('HSET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id, user_id ..'::1');

-- 

local sb = redis.call('HGET', 'prop::'.. group_id, 'back_id');

if (sb) then return 'back_running'; end;

-- 

redis.call('HSET', 'prop::'.. group_id, 'back_id', back_id);

return 'back_init';
