@echo off
set /p name=请输入元素名然后按Enter : 

rename compressed_%name%_plate.*.json compressed_plate_%name%.*.json