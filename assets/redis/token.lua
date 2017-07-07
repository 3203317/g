-- huangxin <3203317@qq.com>

-- 

local db = KEYS[1];

local server_id = ARGV[1];
local channel_id = ARGV[2];

-- 

redis.call('SELECT', db);

local code = ARGV[1];

local exist = redis.call('EXISTS', code);

if 1 ~= exist then return 'invalid_code'; end;

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('DEL', code, client_id ..'::'.. user_id);

-- 

local server_id = ARGV[2];
local channel_id = ARGV[3];
-- local seconds = ARGV[4];

-- 

local result = 'OK';

local s = redis.call('HGET', 'prop::'.. user_id, 'server_id');

if (s) then
  local b = redis.call('HGET', 'prop::'.. user_id, 'channel_id');
  result = s ..'::'.. b;
  redis.call('DEL', result);

  -- 

  local group_id = redis.call('HGET', 'prop::'.. user_id, 'group_id');

  if (group_id) then

    local group_pos_id = redis.call('HGET', 'prop::'.. user_id, 'group_pos_id');

    redis.call('SELECT', 1 + db);

    local group_type = redis.call('HGET', 'prop::'.. group_id, 'type');

    local s2 = redis.call('HGET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id);
    local b2 = s ..'::'.. b ..'::'.. user_id;

    if (s2 == b2) then
      redis.call('HSET', 'pos::'.. group_type ..'::'.. group_id, group_pos_id, server_id ..'::'.. channel_id ..'::'.. user_id);
    end;

  end;

end;

-- 

redis.call('SELECT', db);

redis.call('HMSET', 'prop::'.. user_id, 'client_id', client_id, KEYS[2], server_id, KEYS[3], channel_id, 'scope', '');
-- redis.call('EXPIRE', user_id, seconds);

local _key = server_id ..'::'.. channel_id;

redis.call('SET', _key, user_id);
-- redis.call('EXPIRE', _key, seconds);

return result;
