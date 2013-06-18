#!/bin/bash
MINER_HOME=`dirname $PWD`
echo $MINER_HOME
ps -ef|grep -v grep|grep $MINER_HOME |awk '{print "kill -9 "$2}'|sh
$MINER_HOME/bin/run.bin 2>&1>>std.out &
