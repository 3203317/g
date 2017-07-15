-- huangxin <3203317@qq.com>

-- 

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];

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

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

-- 

local s = redis.call('HGET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'OK'; end;

local b, hand = string.match(s, '(.*)%::(.*)');

if (b ~= user_id) then return 'OK'; end;

-- 

redis.call('HDEL', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

redis.call('SADD', 'idle::groupType::'.. group_type, group_id ..'::'.. group_pos_id);

-- 

local hash_val = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

if (0 == #hash_val) then return 'OK'; end;

-- 

local arr = {};

for i=2, #hash_val, 2 do
  -- table.insert(arr, hash_val[i - 1]);
  local u = string.match(hash_val[i], '(.*)%::(.*)');

  redis.call('SELECT', db);

  local sb = redis.call('HGET', 'prop::'.. u, 'server_id');

  if (sb) then
    table.insert(arr, sb);
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
  else

    redis.call('SELECT', 1 + db);

    local pos = hash_val[i - 1];
    redis.call('HDEL', 'pos::group::'.. group_type ..'::'.. group_id, pos);
    redis.call('SADD', 'idle::groupType::'.. group_type, group_id ..'::'.. pos);
  end;
end;

local result = {};

table.insert(result, arr);
table.insert(result, hash_val);

return result;

