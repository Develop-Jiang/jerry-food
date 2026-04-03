@echo off
chcp 65001 >nul
title Jerry Food 前后端联调环境
color 0A

echo ══════════════════════════════════════════════════════
echo   🏠 Jerry Food 前后端联调 - 一键启动脚本
echo ══════════════════════════════════════════════════════
echo.

cd /d "%~dp0.."

:: ============================================================
:: 第一步：检查Java环境
:: ============================================================
echo [1/6] 检查 Java 环境...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到 Java 环境！
    echo    请确保已安装 JDK 1.8+ 并配置 JAVA_HOME
    pause
    exit /b 1
)
for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do set JAVA_VER=%%i
echo ✅ Java 版本: %JAVA_VER%

:: ============================================================
:: 第二步：检查Maven环境
:: ============================================================
echo.
echo [2/6] 检查 Maven 环境...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到 Maven 环境！
    echo    请确保已安装 Maven 并配置 PATH
    pause
    exit /b 1
)
echo ✅ Maven 环境正常

:: ============================================================
:: 第三步：检查Node.js环境
:: ============================================================
echo.
echo [3/6] 检查 Node.js 环境...
node -v >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到 Node.js 环境！
    echo    请确保已安装 Node.js 并配置 PATH
    pause
    exit /b 1
)
for /f "tokens=1" %%i in ('node -v') do set NODE_VER=%%i
echo ✅ Node.js 版本: %NODE_VER%

:: ============================================================
:: 第四步：检查MySQL连接
:: ============================================================
echo.
echo [4/6] 检查 MySQL 数据库连接...
mysqladmin -u root -proot ping >nul 2>&1
if errorlevel 1 (
    echo ⚠️  警告：无法连接到 MySQL (root/root)
    echo    请确认：
    echo    ① MySQL 服务已启动
    echo    ② 用户名密码正确（当前配置: root/root）
    echo    ③ 数据库 jerry_food 已创建
    echo.
    set /p MYSQL_CHECK="是否继续？(y/n): "
    if /i not "%MYSQL_CHECK%"=="y" (
        pause
        exit /b 1
    )
) else (
    echo ✅ MySQL 连接成功
)

:: ============================================================
:: 第五步：启动Spring Boot后端（端口8080）
:: ============================================================
echo.
echo [5/6] 启动 Spring Boot 后端服务...
echo    📍 后端地址: http://localhost:8080/api
echo    📚 API文档: http://localhost:8080/swagger-ui.html
echo.

cd backend

:: 在后台启动后端
start "Jerry-Food-Backend" cmd /k "echo 正在启动后端服务... && mvn spring-boot:run"

:: 等待后端启动（等待30秒）
echo ⏳ 等待后端服务启动（约30秒）...
timeout /t 30 /nobreak >nul

:: 检查后端是否启动成功
curl -s http://localhost:8080/api/home/info >nul 2>&1
if errorlevel 1 (
    echo ⚠️  警告：后端服务可能还未完全启动
    echo    请稍等片刻或查看后端窗口的日志输出
) else (
    echo ✅ 后端服务启动成功！
)

cd ..

:: ============================================================
:: 第六步：启动Node.js前端（端口3000）
:: ============================================================
echo.
echo [6/6] 启动 Node.js 前端服务...
echo    📍 前端地址: http://localhost:3000
echo.

:: 在后台启动前端
start "Jerry-Food-Frontend" cmd /k "echo 正在启动前端服务... && node server.js"

:: 等待前端启动
timeout /t 5 /nobreak >nul

echo.
echo ══════════════════════════════════════════════════════
echo   🎉 所有服务已启动！
echo ══════════════════════════════════════════════════════
echo.
echo   📱 访问地址：
echo      • 前端页面: http://localhost:3000
echo      • 后端API:  http://localhost:8080/api
echo      • Swagger:   http://localhost:8080/swagger-ui.html
echo.
echo   🔧 联调测试：
echo      • 浏览器打开 http://localhost:3000
echo      • 点击衣柜/冰箱测试交互功能
echo      • 打开浏览器F12查看网络请求
echo.
echo   💡 提示：
echo      • 后端日志在 "Jerry-Food-Backend" 窗口
echo      • 前端日志在 "Jerry-Food-Frontend" 窗口
echo      • 按 Ctrl+C 可停止对应服务
echo.
pause