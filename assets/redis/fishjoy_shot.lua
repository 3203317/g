-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];
local bullet_id  = KEYS[4];

local seconds  = ARGV[1];
local bullet_x = ARGV[2];
local bullet_y = ARGV[3];

redis.call('SELECT', db);

-- 

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 判断子弹id是否存在

local exist = redis.call('EXISTS', 'prop::bullet::'.. user_id ..'::'.. bullet_id);

if (1 == exist) then return 'invalid_bullet_id'; end;

-- 获取用户组id

local group_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 获取组类型

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_type'; end;

-- 

local group_pos_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_pos_id');

-- 

local s = redis.call('HGET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'invalid_group_pos_id'; end;

-- 判断是否是本人

local b, hand = string.match(s, '(.*)::(.*)');

if (b ~= user_id) then return 'invalid_user_id'; end;

if (0 == tonumber(hand)) then return 'invalid_raise_hand' end;

-- 组位置成员列表

local group_pos = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

if (0 == #group_pos) then return 'invalid_group_pos'; end;

-- 获取用户当前的子弹等级

local current_bullet_level = redis.call('HGET', 'prop::user::'.. user_id, 'current_bullet_level');

-- 判断子弹的等级对应的消耗是否存在

local bullet_consume = redis.call('HGET', 'cfg', 'bullet_lv_'.. current_bullet_level ..'_consume');

bullet_consume = tonumber(bullet_consume);

-- 获取用户的积分

local user_score = redis.call('HGET', 'prop::user::'.. user_id, 'score');

user_score = tonumber(user_score) - bullet_consume;

if (0 > user_score) then return 'invalid_user_score'; end;

redis.call('HSET', 'prop::user::'.. user_id, 'score', user_score);

-- 所在组消耗的金币数

local group_consume_score = redis.call('HGET', 'prop::user::'.. user_id, 'group_consume_score');

group_consume_score = tonumber(group_consume_score) + bullet_consume;

redis.call('HSET', 'prop::user::'.. user_id, 'group_consume_score', group_consume_score);

-- 历史以来消耗的金币数

local bullet_consume_count = redis.call('HGET', 'prop::user::'.. user_id, 'bullet_consume_count');

bullet_consume_count = tonumber(bullet_consume_count) + bullet_consume;

redis.call('HSET', 'prop::user::'.. user_id, 'bullet_consume_count', bullet_consume_count);

-- 

redis.call('HMSET', 'prop::bullet::'.. user_id ..'::'.. bullet_id, 'id',           bullet_id,
                                                                   'x',            bullet_x,
                                                                   'y',            bullet_y,
                                                                   'level',        current_bullet_level,
                                                                   'consume',      bullet_consume,
                                                                   'group_id',     group_id,
                                                                   'group_pos_id', group_pos_id,
                                                                   'group_type',   group_type,
                                                                   'user_id',      user_id,
                                                                   'user_score',   user_score);
redis.call('EXPIRE', 'prop::bullet::'.. user_id ..'::'.. bullet_id, seconds);

-- 

local arr1 = {};

for i=2, #group_pos, 2 do
  local u, hand = string.match(group_pos[i], '(.*)::(.*)');

  if ('1' == hand) then
    table.insert(arr1, redis.call('HGET', 'prop::user::'.. u, 'server_id'));
    table.insert(arr1, redis.call('HGET', 'prop::user::'.. u, 'channel_id'));
  end;
end;

-- 

local result = {};

table.insert(result, arr1);
table.insert(result, redis.call('HGETALL', 'prop::bullet::'.. user_id ..'::'.. bullet_id));

return result;
