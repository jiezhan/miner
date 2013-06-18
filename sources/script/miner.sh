#!/bin/bash
MINER_LIB=`dirname $PWD`
echo $MINER_LIB
$MINER_LIB/bin/run.bin 2>&1>>std.out &
