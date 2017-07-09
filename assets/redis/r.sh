#!/bin/bash

/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/authorize.lua)"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/token.lua)"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/server_close.lua)"

/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/channel_close.lua)"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/group_search.lua)"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/group_quit.lua)"


/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/fishjoy_ready.lua)"

/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 --eval /root/my/git/3203317/g/assets/redis/init.lua 1 ,




