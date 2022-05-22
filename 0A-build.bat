@echo off
cd ..
:start
echo.
echo 0 = build path
echo 1 = build eclipse path
echo 2 = build mod
echo.
set /p a="path: "
echo.

if %a% == 0 gradlew setupDecompWorkspace
if %a% == 1 gradlew setupDecompWorkspace eclipse
if %a% == 2 goto Build

:Build
rmdir /q /s build\classes
rmdir /q /s build\dependency-cache
rmdir /q /s build\resources
rmdir /q /s build\retromapping
rmdir /q /s build\sources
rmdir /q /s build\taskLogs
rmdir /q /s build\tmp

gradlew build

goto start