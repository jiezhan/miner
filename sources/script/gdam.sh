#!/bin/bash
GDAM_LIB=`dirname $PWD`
echo $GDAM_LIB
$GDAM_LIB/bin/run.bin 2>&1>>std.out &
