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

-- 

local group_pos_id = redis.call('HGET', 'prop::'.. user_id, 'group_pos_id');

redis.call('HDEL', 'prop::'.. user_id, 'group_id', 'group_pos_id');

-- 获取群组的类型

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::'.. group_id, 'type');

-- 

local sb = redis.call('HGET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);

if (sb ~= user_id) then return 'OK'; end;

-- 

redis.call('HDEL', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);

redis.call('SADD', 'idle::'.. group_type, group_id ..'::'.. group_pos_id);

-- 

local hash_val = redis.call('HGETALL', 'pos::'.. group_type ..'::'.. group_id);

redis.call('SELECT', db);

local arr = {};

for i=2, #hash_val, 2 do
  -- table.insert(result, hash_val[i - 1]);
  local u = hash_val[i];

  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'server_id'));
  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
end;

local result = {};

table.insert(result, arr);
table.insert(result, hash_val);

return result;

