-- huangxin <3203317@qq.com>

local db      = KEYS[1];
local user_id = KEYS[2];
local fish_id = KEYS[3];

local fish_type    = ARGV[1];
local money        = ARGV[2];
local current_time = ARGV[3];

-- 

redis.call('SELECT', 1 + db);

local exist = redis.call('EXISTS', 'dead::'.. fish_id);

if (1 == exist) then return 'invalid_dead_fish_id'; end;

-- 

redis.call('SELECT', db);

local group_id = redis.call('HGET', 'prop::user::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

if (false == group_type) then return 'invalid_group_type'; end;

local group_pos = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

if (0 == #group_pos) then return 'invalid_group_pos'; end;

-- 

redis.call('HMSET', 'dead::'.. fish_id, 'user_id',      user_id,
                                        'fish_type',    fish_type,
                                        'money',        money,
                                        'current_time', current_time);

-- 

redis.call('SELECT', db);

local x = redis.call('HGET', 'prop::user::'.. user_id, 'score');

local y = tonumber(x) + tonumber(money);

redis.call('HSET', 'prop::user::'.. user_id, 'score', y);

-- 

local arr = {};

for i=2, #group_pos, 2 do
  local u, hand = string.match(group_pos[i], '(.*)%::(.*)');

  if ('1' == hand) then
    table.insert(arr, redis.call('HGET', 'prop::user::'.. u, 'server_id'));
    table.insert(arr, redis.call('HGET', 'prop::user::'.. u, 'channel_id'));
  end;

end;

-- 

local result = {};

table.insert(result, arr);
table.insert(result, y);

return result;
