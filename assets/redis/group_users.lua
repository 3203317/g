-- huangxin <3203317@qq.com>

local db       = KEYS[1];
local group_id = KEYS[2];

-- 获取群组的类型

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_id'; end;

local group_pos_info = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

-- 

local result = {};

for i=2, #group_pos_info, 2 do
  local u, hand = string.match(group_pos_info[i], '(.*)%::(.*)');

  redis.call('SELECT', db);

  local sb = redis.call('HGET', 'prop::'.. u, 'server_id');

  if (sb) then
    table.insert(result, sb);
    table.insert(result, redis.call('HGET', 'prop::'.. u, 'channel_id'));
    table.insert(result, hand);
  else

    redis.call('SELECT', 1 + db);

    local pos = group_pos_info[i - 1];
    redis.call('HDEL', 'pos::group::'.. group_type ..'::'.. group_id, pos);
    redis.call('SADD', 'idle::groupType::'.. group_type, group_id ..'::'.. pos);
  end;

end;

return result;
