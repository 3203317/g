-- huangxin <3203317@qq.com>

local db         = KEYS[1];
local server_id  = KEYS[2];
local channel_id = KEYS[3];
local bullet_id  = KEYS[4];

-- 

redis.call('SELECT', db);

local user_id = redis.call('GET', server_id ..'::'.. channel_id);

if (false == user_id) then return 'invalid_user_id'; end;

-- 

redis.call('SELECT', 1 + db);

local bullet_info = redis.call('HGETALL', 'prop::bullet::'.. user_id ..'::'.. bullet_id);

if (false == bullet_info) then return 'invalid_bullet_id'; end;

-- 

redis.call('SELECT', db);

local user_info = redis.call('HGETALL', 'prop::'.. user_id);

-- 

local result = {};

table.insert(result, user_info);
table.insert(result, bullet_info);

return result;
