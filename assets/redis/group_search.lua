-- huangxin <3203317@qq.com>

redis.replicate_commands();

----
local key_0 = '::';

local db = KEYS[1];
local group_uuid = KEYS[2];

local server_id = ARGV[1];
local channel_id = ARGV[2];
local group_type = ARGV[3];

----

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id .. key_0 .. channel_id);

if (false == user_id) then return 'invalid_channel'; end;

----

redis.call('SELECT', 1 + db);

local total_players = redis.call('GET', group_type .. key_0 .. 'total_players');

if (false == total_players) then return 'invalid_group_type'; end;

--

local idle_group = redis.call('SPOP', group_type .. key_0 .. 'idle');

if (false == idle_group) then

  for i = 1, tonumber(total_players) do
    redis.call('SADD', group_type .. key_0 .. 'idle', group_uuid .. key_0 .. i);
  end;

  idle_group = redis.call('SPOP', group_type .. key_0 .. 'idle');

  if (false == idle_group) then return 'non_idle_group'; end;
end;

local group_id, group_pos_id = string.match(idle_group, '(.*)%::(.*)');

----

redis.call('SELECT', 2 + db);

redis.call('HMSET', group_type .. key_0 .. group_id ..'::pos', group_pos_id, server_id .. key_0 .. channel_id .. key_0 .. user_id);

----

redis.call('SELECT', db);

redis.call('HMSET', user_id, 'group_id', group_id, 'group_pos_id', group_pos_id, 'group_type', group_type);

----

redis.call('SELECT', 2 + db);

local result = redis.call('HGETALL', group_type .. key_0 .. group_id ..'::pos');

return result;


