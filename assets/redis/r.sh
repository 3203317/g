#!/bin/bash

echo "authorize.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/authorize.lua)"

echo ""
echo "token.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/token.lua)"

echo ""
echo "channel_close.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/channel_close.lua)"

echo ""
echo "back_open.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/back_open.lua)"

echo ""
echo "back_close.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/back_close.lua)"

echo ""
echo "group_search.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/group_search.lua)"

echo ""
echo "group_quit"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/group_quit.lua)"

echo ""
echo "fishjoy_ready.lua"
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 script load "$(cat /root/my/git/3203317/g/assets/redis/fishjoy_ready.lua)"

echo ""
/root/my/redis/redis-3.2.6/src/redis-cli -a 123456 --eval /root/my/git/3203317/g/assets/redis/init.lua 1 ,




