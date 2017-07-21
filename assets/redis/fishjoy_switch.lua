-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];

local bullet_level  = ARGV[1];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

local x = redis.call('HGET', 'prop::'.. user_id, 'bullet_level');

if (tonumber(x) < tonumber(bullet_level)) then return 'invalid_bullet_level'; end;

-- 

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_type'; end;

local group_pos_info = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

-- 

redis.call('SELECT', db);

-- 为用户设置当前使用的子弹等级

redis.call('HSET', 'prop::'.. user_id, 'current_bullet_level', bullet_level);

-- 

local arr = {};

for i=2, #group_pos_info, 2 do
  local u, hand = string.match(group_pos_info[i], '(.*)%::(.*)');

  if ('1' == hand) then
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'server_id'));
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
  end;

end;

local result = {};

table.insert(result, arr);
table.insert(result, user_id);

return result;
