#!/bin/bash
MINER_HOME=`dirname $PWD`
echo $MINER_HOME
ps -ef|grep -v grep|grep $MINER_HOME |awk '{print "kill -9 "$2}'|sh
