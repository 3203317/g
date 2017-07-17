-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];

local back_id    = ARGV[1];
local open_time  = ARGV[2];

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

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

-- 

local s = redis.call('HGET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'invalid_group_pos_id'; end;

-- 判断是否是本人

local b, hand = string.match(s, '(.*)%::(.*)');

if (b ~= user_id) then return 'invalid_user_id'; end;

if (1 == tonumber(hand)) then return 'already_raise_hand' end;

-- 

redis.call('HSET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id, user_id ..'::1');

-- 群组对应的后置服务器

local sb = redis.call('HGET', 'prop::group::'.. group_id, 'back_id');

if (false == sb) then

  redis.call('HMSET', 'prop::group::'.. group_id, 'back_id', back_id, 'open_time', open_time);
else

  -- 后置服务器的启动时间

  local x = redis.call('HGET', 'prop::back::'.. sb, 'open_time');

  if (false == x) then

    redis.call('HMSET', 'prop::group::'.. group_id, 'back_id', back_id, 'open_time', open_time);
  else

    local y = redis.call('HGET', 'prop::group::'.. group_id, 'open_time');

    if (tonumber(x) < tonumber(y)) then

      back_id = sb;
    else

      redis.call('HMSET', 'prop::group::'.. group_id, 'back_id', back_id, 'open_time', open_time);
    end;

  end;

end;

-- 

local hash_val = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

local group_info = redis.call('HGETALL', 'prop::group::'.. group_id);

-- 

local arr = {};

local user_info = {};

for i=2, #hash_val, 2 do
  local u = string.match(hash_val[i], '(.*)%::(.*)');

  redis.call('SELECT', db);

  local dsb = redis.call('HGET', 'prop::'.. u, 'server_id');

  if (dsb) then
    table.insert(arr, dsb);
    table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));

    -- 

    table.insert(user_info, redis.call('HGET', 'prop::'.. u, 'extend_data'));
    table.insert(user_info, redis.call('HGET', 'prop::'.. u, 'open_time'));
  else

    redis.call('SELECT', 1 + db);

    local pos = hash_val[i - 1];
    redis.call('HDEL', 'pos::group::'.. group_type ..'::'.. group_id, pos);
    redis.call('SADD', 'idle::groupType::'.. group_type, group_id ..'::'.. pos);
  end;

end;

local result = {};

table.insert(result, arr);
table.insert(result, [user_info, group_info, hash_val]);

return result;
