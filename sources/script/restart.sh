#!/bin/bash
GDAM_HOME=`dirname $PWD`
echo $GDAM_HOME
ps -ef|grep -v grep|grep $GDAM_HOME |awk '{print "kill -9 "$2}'|sh
$GDAM_HOME/bin/run.bin 2>&1>>std.out &
