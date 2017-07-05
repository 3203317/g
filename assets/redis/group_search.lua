-- huangxin <3203317@qq.com>

redis.replicate_commands();

----

local db = KEYS[1];
local group_uuid = KEYS[2];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];

----

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

----

redis.call('SELECT', 1 + db);

local exist = redis.call('EXISTS', 'group::'.. group_type);

if (1 ~= exist) then return 'invalid_group_type'; end;

--

local idle_group = redis.call('SPOP', 'idle::'.. group_type);

if (false == idle_group) then

  local total_users = redis.call('HGET', 'group::'.. group_type, 'total_users');

  for i = 1, tonumber(total_users) do
    redis.call('SADD', 'idle::'.. group_type, group_uuid ..'::'.. i);
  end;

  redis.call('HSET', 'prop::'.. group_uuid, 'type', group_type);

  idle_group = redis.call('SPOP', 'idle::'.. group_type);

  if (false == idle_group) then return 'non_idle_group'; end;
end;

local group_id, group_pos_id = string.match(idle_group, '(.*)%::(.*)');

----

redis.call('SELECT', 2 + db);

redis.call('HMSET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id, server_id ..'::'.. channel_id ..'::'.. user_id);

----

redis.call('SELECT', db);

redis.call('HMSET', user_id, 'group_id', group_id, 'group_pos_id', group_pos_id);

----

redis.call('SELECT', 2 + db);

local result = redis.call('HGETALL', 'pos::'.. group_type ..'::'.. group_id);

return result;


