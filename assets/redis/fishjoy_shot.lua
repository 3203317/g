-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];
local bullet_id  = KEYS[4];

local seconds      = ARGV[1];
local bullet_x     = ARGV[2];
local bullet_y     = ARGV[3];
local bullet_level = ARGV[4];

-- 

redis.call('SELECT', 1 + db);

local bullet_consume = redis.call('HGET', 'cfg::bullet::consume', bullet_level);

if (false == bullet_consume) then return 'invalid_bullet_level'; end;

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

local max_bullet_level = redis.call('HGET', 'prop::'.. user_id, 'bullet_level');

if (tonumber(max_bullet_level) < tonumber(bullet_level)) then return 'invalid_bullet_level'; end;

-- 不在任何群组

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

local group_pos_id = redis.call('HGET', 'prop::'.. user_id, 'group_pos_id');

-- 

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

local s = redis.call('HGET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'invalid_group_pos_id'; end;

-- 判断是否是本人

local b, hand = string.match(s, '(.*)%::(.*)');

if (b ~= user_id) then return 'invalid_user_id'; end;

if (0 == tonumber(hand)) then return 'invalid_raise_hand' end;

-- 

redis.call('SELECT', db);

local user_score = redis.call('HGET', 'prop::'.. user_id, 'score');

local x = tonumber(user_score) - tonumber(bullet_consume);

if (0 > x) then return 'invalid_user_score'; end;

redis.call('HSET', 'prop::'.. user_id, 'score', x);

-- 

redis.call('SELECT', 1 + db);

redis.call('HMSET', 'prop::bullet::'.. user_id ..'::'.. bullet_id, 'id',           bullet_id,
                                                                   'x',            bullet_x,
                                                                   'y',            bullet_y,
                                                                   'level',        bullet_level,
                                                                   'group_id',     group_id,
                                                                   'group_pos_id', group_pos_id,
                                                                   'group_type',   group_type,
                                                                   'user_id',      user_id);
redis.call('EXPIRE', 'prop::bullet::'.. user_id ..'::'.. bullet_id, seconds);

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

local user_info = {};

table.insert(user_info, user_id);
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'extend_data'));
table.insert(user_info, x);
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_1'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_2'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_3'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_4'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_5'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_6'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_7'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_8'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'tool_9'));
-- table.insert(user_info, redis.call('HGET', 'prop::'.. user_id, 'open_time'));

redis.call('SELECT', 1 + db);

local result = {};

table.insert(result, arr);

-- 

local xx = {};

table.insert(xx, user_info);
table.insert(xx, redis.call('HGETALL', 'prop::bullet::'.. user_id ..'::'.. bullet_id));

-- 

table.insert(result, xx);

return result;
