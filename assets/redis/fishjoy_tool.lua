-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];

local tool_type  = ARGV[1];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

local x = tonumber(redis.call('HGET', 'prop::'.. user_id, 'tool_'.. tool_type));

if (0 == x) then return 'invalid_tool_'.. tool_type; end;

local y = x - 1;

redis.call('HSET', 'prop::'.. user_id, 'tool_'.. tool_type, y);

-- 

redis.call('SELECT', 1 + db);

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_type'; end;

local group_pos_info = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

-- 

redis.call('SELECT', db);

local arr = {};

for i=2, #group_pos_info, 2 do
  local u, hand = string.match(group_pos_info[i], '(.*)%::(.*)');

  if ('1' == hand) then
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'server_id'));
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
  end;

end;

local sb = {};

table.insert(sb, user_id);
table.insert(sb, y);

local result = {};

table.insert(result, arr);
table.insert(result, sb);

return result;
