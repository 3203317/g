
-- huangxin <3203317@qq.com>

redis.call('SELECT', 8);

local code = ARGV[1];

local exist = redis.call('EXISTS', code);

if 1 ~= exist then return '401'; end;

local client_id = redis.call('HGET', code, 'client_id');
local user_id = redis.call('HGET', code, 'user_id');

redis.call('DEL', client_id ..'::'.. user_id);
redis.call('DEL', code);




redis.call('SELECT', 6);

local channel_id = ARGV[2];
local seconds = ARGV[3];
local server_id = ARGV[4];

redis.call('HMSET', user_id, 'client_id', client_id, KEYS[4], server_id, KEYS[2], channel_id, 'scope', '');
redis.call('EXPIRE', user_id, seconds);

local _key = server_id ..'::'.. channel_id;

redis.call('HMSET', _key, 'client_id', client_id, 'user_id', user_id, 'scope', '');
redis.call('EXPIRE', _key, seconds);




return 'OK';