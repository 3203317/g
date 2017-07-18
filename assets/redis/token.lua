-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];
local code       = KEYS[4];

local seconds    = ARGV[1];
local open_time  = ARGV[2];

-- 

redis.call('SELECT', db);

local exist = redis.call('EXISTS', code);

if (1 ~= exist) then return 'invalid_code'; end;

-- 

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('DEL', client_id ..'::'.. user_id);

-- 

local result = 'OK';

-- 

local s = redis.call('HGET', 'prop::'.. user_id, 'server_id');

if (s) then
  local b = redis.call('HGET', 'prop::'.. user_id, 'channel_id');
  result = s ..'::'.. b;
  redis.call('DEL', result);
end;

-- 

local sb = redis.call('HGET', 'prop::'.. user_id, 'group_id');

if (sb) then
  redis.call('HMSET', code, 'group_id', sb,
                            'group_pos_id', redis.call('HGET', 'prop::'.. user_id, 'group_pos_id'));
end;

-- 

redis.call('RENAME', code, 'prop::'.. user_id);

-- 

redis.call('HDEL', 'prop::'.. user_id, 'user_id');
redis.call('HMSET', 'prop::'.. user_id, 'server_id', server_id,
                                        'channel_id', channel_id,
                                        'open_time', open_time);
redis.call('EXPIRE', 'prop::'.. user_id, seconds);

redis.call('SET', server_id ..'::'.. channel_id, user_id);
redis.call('EXPIRE', server_id ..'::'.. channel_id, seconds);

-- 

return result;
