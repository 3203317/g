-- huangxin <3203317@qq.com>

redis.replicate_commands();

-- 

local db = KEYS[1];
local group_uuid = KEYS[2];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

redis.call('SELECT', 1 + db);

local exist = redis.call('EXISTS', 'prop::'.. group_type);

if (1 ~= exist) then return 'invalid_group_type'; end;

-- 找一个空闲的群组

local idle_group = redis.call('SPOP', 'idle::'.. group_type);

if (false == idle_group) then

  local total_players = redis.call('HGET', 'prop::'.. group_type, 'total_players');

  for i=1, tonumber(total_players) do
    redis.call('SADD', 'idle::'.. group_type, group_uuid ..'::'.. i);
  end;

  -- 为新创建的群组设置群组类型

  local capacity = redis.call('HGET', 'prop::'.. group_type, 'capacity');

  redis.call('HMSET', 'prop::'.. group_uuid, 'type', group_type, 'capacity', capacity);

  -- 再次找一个空闲的群组

  idle_group = redis.call('SPOP', 'idle::'.. group_type);

  -- 又没有找到

  if (false == idle_group) then return 'non_idle_group'; end;
end;

-- 

local group_id, group_pos_id = string.match(idle_group, '(.*)%::(.*)');

-- 

redis.call('HSET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id, user_id ..'::0');

-- 

local hash_val = redis.call('HGETALL', 'pos::'.. group_type ..'::'.. group_id);

-- 

redis.call('SELECT', db);

redis.call('HMSET', 'prop::'.. user_id, 'group_id', group_id, 'group_pos_id', group_pos_id);

local arr = {};

for i=2, #hash_val, 2 do
  local u = string.match(hash_val[i], '(.*)%::(.*)');

  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'server_id'));
  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
end;

local result = {};

table.insert(result, arr);
table.insert(result, hash_val);

return result;

