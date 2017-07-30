-- huangxin <3203317@qq.com>

local db      = KEYS[1];
local user_id = KEYS[2];
local fish_id = KEYS[3];

local seconds      = ARGV[1];
local fish_type    = ARGV[2];
local money        = ARGV[3];
local gift         = ARGV[4];
local current_time = ARGV[5];

redis.call('SELECT', db);

-- 

local exist = redis.call('EXISTS', 'dead::'.. fish_id);

if (1 == exist) then return 'invalid_dead_fish_id'; end;

-- 

local group_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_type'; end;

-- 

local group_pos = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

if (0 == #group_pos) then return 'invalid_group_pos'; end;

-- 

redis.call('HMSET', 'dead::'.. fish_id, 'user_id',      user_id,
                                        'fish_type',    fish_type,
                                        'money',        money,
                                        'gift',         gift,
                                        'current_time', current_time);

redis.call('EXPIRE', 'dead::'.. fish_id, seconds);

-- 

money = tonumber(money);

gift  = tonumber(gift);

-- 用户现有总金币数

local user_score = redis.call('HGET', 'prop::user::'.. user_id, 'score');

user_score = tonumber(user_score) + money;

redis.call('HSET', 'prop::user::'.. user_id, 'score', user_score);

-- 所在组获得的金币数

local group_gain_score = redis.call('HGET', 'prop::user::'.. user_id, 'group_gain_score');

group_gain_score = tonumber(group_gain_score) + money;

redis.call('HSET', 'prop::user::'.. user_id, 'group_gain_score', group_gain_score);

-- 用户现有礼券数

local gift_count = redis.call('HGET', 'prop::user::'.. user_id, 'gift_count');

gift_count = tonumber(gift_count) + gift;

redis.call('HSET', 'prop::user::'.. user_id, 'gift_count', gift_count);

-- 历史以来获得的金币数

local gain_score_count = redis.call('HGET', 'prop::user::'.. user_id, 'gain_score_count');

gain_score_count = tonumber(gain_score_count) + money;

redis.call('HSET', 'prop::user::'.. user_id, 'gain_score_count', gain_score_count);

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

local arr2 = {};

table.insert(arr2, user_score);           -- 用户现有总金币数
table.insert(arr2, group_gain_score);     -- 所在组获得的金币数
table.insert(arr2, gift_count);           -- 用户现有礼券数
table.insert(arr2, gain_score_count);     -- 历史以来获得的金币数

-- 

local result = {};

table.insert(result, arr1);
table.insert(result, arr2);

return result;
