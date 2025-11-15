@echo off

cd /d %~dp0

echo --
echo Getting rid of old Build
ant clean

echo --
echo Building project...
ant build

echo --
echo Done Building
pause
