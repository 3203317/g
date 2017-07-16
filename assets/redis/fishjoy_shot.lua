-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];
local bullet_id  = ARGV[4];

local seconds      = ARGV[1];
local bullet_x     = ARGV[2];
local bullet_y     = ARGV[3];
local bullet_level = ARGV[4];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 不在任何群组

local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (false == group_id) then return 'invalid_group_id'; end;

-- 获取群组的类型

redis.call('SELECT', 1 + db);

local group_type = redis.call('HGET', 'prop::group::'.. group_id, 'type');

-- 

local score = redis.call('HGET', 'prop::'.. user_id, 'score');

-- 

redis.call('SELECT', 1 + db);

redis.call('HMSET', 'prop::bullet::'.. bullet_id, 'x', bullet_x, 'y', bullet_y, 'level', bullet_level, 'user_id', user_id);
redis.call('EXPIRE', 'prop::bullet::'.. bullet_id, seconds);

-- 

local x = redis.call('HGET', 'cfg::bullet::consume', bullet_level);

redis.call('SELECT', db);

redis.call('HSET', 'prop::'.. user_id, tonumber(score) - tonumber(x));

-- 

redis.call('SELECT', 1 + db);

local hash_val = redis.call('HGETALL', 'pos::group::'.. group_type ..'::'.. group_id);

-- 

redis.call('SELECT', db);

local arr = {};

for i=2, #hash_val, 2 do
  local u = string.match(hash_val[i], '(.*)%::(.*)');

  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'server_id');
  table.insert(arr, redis.call('HGET', 'prop::'.. u, 'channel_id'));
end;

local result = {};

table.insert(result, arr);
table.insert(result, redis.call('HGETALL', 'prop::bullet::'.. bullet_id));

return result;

