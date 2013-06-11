set GDAM_HOME =%cd%/..
echo %GDAM_HOME%
java -classpath .;$CLASS_PATH;%GDAM_HOME%/lib/* com.isoftstone.gdam.test.Test