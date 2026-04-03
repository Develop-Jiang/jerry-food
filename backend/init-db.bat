@echo off
chcp 65001 >nul
echo ============================================
echo   Jerry Food 数据库初始化脚本
echo ============================================
echo.

cd /d "%~dp0"

set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

if not exist %MYSQL_PATH% (
    echo ⚠️  未找到默认MySQL路径，请手动指定MySQL路径
    set /p MYSQL_PATH="请输入mysql.exe的完整路径: "
)

echo.
echo 请输入MySQL连接信息：
set /p MYSQL_USER="用户名 (默认root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASS="密码 (默认root): "
if "%MYSQL_PASS%"=="" set MYSQL_PASS=root

echo.
echo 正在初始化数据库...
echo.

%MYSQL_PATH% -u%MYSQL_USER% -p%MYSQL_PASS% < src\main\resources\db\init.sql

if errorlevel 1 (
    echo.
    echo ❌ 数据库初始化失败！
    echo 可能的原因：
    echo   1. MySQL服务未启动
    echo   2. 用户名或密码错误
    echo   3. MySQL路径不正确
) else (
    echo.
    echo ✅ 数据库初始化成功！
    echo.
    echo 已创建以下内容：
    echo   - 数据库：jerry_food
    echo   - 表结构：4张数据表
    echo   - 初始数据：示例衣物和样式
)

echo.
pause