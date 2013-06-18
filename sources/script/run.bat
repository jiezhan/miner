set MINER_HOME =%cd%/..
echo %MINER_HOME%
java -classpath .;$CLASS_PATH;%MINER_HOME%/lib/* com.ly.miner.app.Miner