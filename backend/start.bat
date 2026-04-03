@echo off
chcp 65001 >nul
echo ============================================
echo   Jerry Food 后端服务启动脚本
echo ============================================
echo.

cd /d "%~dp0"

echo [1/3] 检查Java环境...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到Java环境，请先安装JDK 11+
    pause
    exit /b 1
)
echo ✅ Java环境检查通过

echo.
echo [2/3] 检查Maven环境...
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误：未找到Maven，请先安装Maven
    pause
    exit /b 1
)
echo ✅ Maven环境检查通过

echo.
echo [3/3] 启动Spring Boot服务...
echo.
echo 📍 服务地址: http://localhost:8080/api
echo 📚 API文档: http://localhost:8080/swagger-ui.html
echo.
echo ============================================

mvn spring-boot:run

pause