@echo off
setlocal ENABLEDELAYEDEXPANSION

set EXE_DIR=%~dp0
FOR /R %EXE_DIR%\lib %%G IN (*.jar) DO set CLASSPATH=!CLASSPATH!;%%G

java net.padlocksoftware.padlock.tools.KeyMaker %*

