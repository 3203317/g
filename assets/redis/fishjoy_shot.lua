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

redis.call('SELECT', db);

local bullet_consume = redis.call('HGET', 'cfg::bullet::consume', bullet_level);

if (false == bullet_consume) then return 'invalid_bullet_level'; end;

-- 

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

local max_bullet_level = redis.call('HGET', 'prop::user::'.. user_id, 'bullet_level');

if (tonumber(max_bullet_level) < tonumber(bullet_level)) then return 'invalid_bullet_level'; end;

-- 不在任何群组

local group_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

local group_pos_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_pos_id');

-- 

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

local s = redis.call('HGET', 'pos::group::'.. group_type ..'::'.. group_id, group_pos_id);

if (false == s) then return 'invalid_group_pos_id'; end;

-- 判断是否是本人

local b, hand = string.match(s, '(.*)%::(.*)');

if (b ~= user_id) then return 'invalid_user_id'; end;

if (0 == tonumber(hand)) then return 'invalid_raise_hand' end;

-- 

local user_score = redis.call('HGET', 'prop::user::'.. user_id, 'score');

local current_score = tonumber(user_score) - tonumber(bullet_consume);

if (0 > current_score) then return 'invalid_user_score'; end;

redis.call('HSET', 'prop::user::'.. user_id, 'score', current_score);

-- 

redis.call('HMSET', 'prop::bullet::'.. user_id ..'::'.. bullet_id, 'id',           bullet_id,
                                                                   'x',            bullet_x,
                                                                   'y',            bullet_y,
                                                                   'level',        bullet_level,
                                                                   'group_id',     group_id,
                                                                   'group_pos_id', group_pos_id,
                                                                   'group_type',   group_type,
                                                                   'user_id',      user_id);
redis.call('EXPIRE', 'prop::bullet::'.. user_id ..'::'.. bullet_id, seconds);

local group_pos = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

if (0 == #group_pos) then return 'invalid_group_pos'; end;

-- 

local arr1 = {};

for i=2, #group_pos, 2 do
  local u, hand = string.match(group_pos[i], '(.*)%::(.*)');

  if ('1' == hand) then
    table.insert(arr1, redis.call('HGET', 'prop::user::'.. u, 'server_id'));
    table.insert(arr1, redis.call('HGET', 'prop::user::'.. u, 'channel_id'));
  end;
end;

local user_info = {};

table.insert(user_info, user_id);
table.insert(user_info, current_score);

local arr2 = {};

table.insert(arr2, user_info);
table.insert(arr2, redis.call('HGETALL', 'prop::bullet::'.. user_id ..'::'.. bullet_id));

local result = {};

table.insert(result, arr1);
table.insert(result, arr2);

return result;
