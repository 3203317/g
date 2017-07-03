-- huangxin <3203317@qq.com>

redis.replicate_commands();

-- 
local key0 = '::';

local server_id = ARGV[1];
local channel_id = ARGV[2];
local game_name = ARGV[3]
local group_type = ARGV[4];

-- // 根据服务器ID+通道ID获取用户ID

redis.call('SELECT', 6);

local user_id = redis.call('GET', server_id .. key0 .. channel_id);

if (false == user_id) then return 'invalid_channel'; end;

-- // 根据群组类型获取对应的数据库

redis.call('SELECT', 0);

-- local db = redis.call('HMGET', 'fishjoy::group_type::1', 'db');
local db = redis.call('HMGET', game_name .. key0 ..'group_type'.. key0 .. group_type, 'db')[1];

if (false == db) then return 'invalid_database'; end;

local total_players = redis.call('HMGET', game_name .. key0 ..'group_type'.. key0 .. group_type, 'total_players')[1];

-- // 随机获取一个群组中的座位（空闲）并占用该座位，然后将该座位移动到另一个库

redis.call('SELECT', db);

local ran_group_pos = redis.call('RANDOMKEY');

-- if (false == ran_group_pos) then return 'non_idle_pos'; end;

if (false == ran_group_pos) then

  -- 创建群组::座位

  for i = 1, tonumber(total_players) do
    redis.call('SET', KEYS[1] .. key0 .. i, 'nil');
  end;

  ran_group_pos = redis.call('RANDOMKEY');
end;

redis.call('MOVE', ran_group_pos, 1 + db);

-- // 切换到另一个库，绑定座位（忙）和用户ID

redis.call('SELECT', 1 + db);

redis.call('SET', ran_group_pos, user_id);

-- // 修改用户信息

redis.call('SELECT', 6);

local group_id, group_pos_id = string.match(ran_group_pos, "(.*)%::(.*)");

redis.call('HMSET', user_id, 'group_id', group_id, 'group_pos_id', group_pos_id, 'group_db', db);

-- // 向群组座位添加用户信息（服务器ID::通道ID::用户ID）

redis.call('SELECT', 2 + db);

redis.call('HMSET', group_id ..'::pos', group_pos_id, server_id ..'::'.. channel_id ..'::'.. user_id);

-- // 获取当前群组成员（玩家和游客）

local result = redis.call('HGETALL', group_id ..'::pos');

return result;
