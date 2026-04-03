# 🚀 Jerry Food 前后端联调指南

## 📋 联调环境概览

### 服务架构图

```
┌─────────────────────────────────────────────────────┐
│                    用户浏览器/小程序                   │
│                      (客户端)                        │
└───────────────────┬─────────────────────────────────┘
                    │ HTTP请求
                    ▼
┌─────────────────────────────────────────────────────┐
│              Node.js 前端服务 (端口 3000)             │
│         • 提供静态HTML页面                           │
│         • 代理API请求到后端                            │
└───────────────────┬─────────────────────────────────┘
                    │ API调用 (http://localhost:8080/api)
                    ▼
┌─────────────────────────────────────────────────────┐
│          Spring Boot 后端服务 (端口 8080)            │
│         • RESTful API接口                           │
│         • 业务逻辑处理                               │
│         • 数据库CRUD操作                             │
└───────────────────┬─────────────────────────────────┘
                    │ JDBC连接
                    ▼
┌─────────────────────────────────────────────────────┐
│               MySQL 数据库 (端口 3306)                │
│         • 数据持久化存储                              │
└─────────────────────────────────────────────────────┘
```

### 端口分配表

| 服务 | 端口 | 用途 | 访问地址 |
|------|------|------|----------|
| **MySQL** | 3306 | 数据库存储 | localhost:3306 |
| **Spring Boot** | 8080 | 后端API服务 | http://localhost:8080/api |
| **Node.js** | 3000 | 前端静态页面 | http://localhost:3000 |

---

## ⚡ 快速启动（3种方式）

### 方式1：一键启动脚本（推荐）⭐

**适用场景：** 首次联调或快速测试

```bash
# 双击运行（在项目根目录）
start-all.bat
```

**脚本功能：**
- ✅ 自动检查 Java/Maven/Node.js 环境
- ✅ 检测 MySQL 连接状态
- ✅ 启动 Spring Boot 后端（新窗口）
- ✅ 启动 Node.js 前端（新窗口）
- ✅ 显示所有访问地址

**输出示例：**
```
✅ Java 版本: 1.8.0_281
✅ Maven 环境正常
✅ Node.js 版本: v18.x.x
✅ MySQL 连接成功

🎉 所有服务已启动！

📱 访问地址：
   • 前端页面: http://localhost:3000
   • 后端API:  http://localhost:8080/api
   • Swagger:   http://localhost:8080/swagger-ui.html
```

---

### 方式2：分步手动启动

**适用场景：** 需要单独调试某个服务

#### 第1步：启动MySQL数据库

```bash
# 检查MySQL是否已启动
mysqladmin -u root -proot ping

# 如果未启动，手动启动MySQL服务
net start mysql
```

**验证数据库：**
```bash
# 登录MySQL
mysql -u root -proot

# 查看数据库
SHOW DATABASES;
# 应该看到 jerry_food 数据库

# 使用数据库
USE jerry_food;

# 查看表
SHOW TABLES;
# 应该看到4张表：home_info, wardrobe_style, fridge_style, clothing
```

---

#### 第2步：启动Spring Boot后端

```bash
cd backend

# 方式A：使用Maven启动（推荐开发时使用）
mvn spring-boot:run

# 方式B：打包后启动（推荐生产环境）
mvn clean package -DskipTests
java -jar target/jerry-food-backend-1.0.0.jar
```

**等待启动完成（约30秒）：**
```
看到以下输出表示成功：
============================================
   🏠 Jerry Food Backend Service Started!  
   📍 http://localhost:8080/api            
   📚 Swagger: http://localhost:8080/swagger-ui.html
============================================
```

**验证后端：**
```bash
# 测试API是否正常
curl http://localhost:8080/api/home/info

# 应该返回JSON：
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "homeName": "我的温馨小家",
    ...
  }
}
```

---

#### 第3步：启动Node.js前端

```bash
# 回到项目根目录
cd ..

# 启动Node.js服务器
node server.js
```

**验证前端：**
```
本地服务器运行在 http://localhost:3000
测试接口: http://localhost:3000/api/test
健康检查: http://localhost:3000/api/health
衣柜接口: http://localhost:3000/api/wardrobe
冰箱接口: http://localhost:3000/api/fridge
```

---

### 方式3：IDE中启动

#### IntelliJ IDEA 启动

1. **打开项目**
   ```
   File → Open → 选择 jerry-food 目录
   ```

2. **导入Maven项目**
   ```
   右键点击 pom.xml → Maven → Reimport Project
   ```

3. **配置后端启动**
   ```
   找到 JerryFoodApplication.java
   → 右键 → Run 'JerryFoodApplication'
   或点击类名左侧的绿色三角形
   ```

4. **配置前端启动（可选）**
   ```
   创建新的 Run Configuration:
   • Type: Node.js
   • File: server.js
   • Working directory: 项目根目录
   ```

5. **同时启动两个服务**
   ```
   先启动后端（Spring Boot）
   再启动前端（Node.js）
   ```

---

## 🔗 联调测试流程

### 测试1：基础连通性测试

**目标：** 验证前后端是否能正常通信

#### 步骤：

1. **打开浏览器访问前端**
   ```
   http://localhost:3000
   ```

2. **按F12打开开发者工具**

3. **切换到 Network（网络）标签页**

4. **点击页面上的"衣柜"按钮**

5. **观察Network中的请求**

**预期结果：**
```
应该看到一个API请求：
• Request URL: http://localhost:8080/api/clothing/list?homeId=1
• Request Method: GET
• Status Code: 200 OK
• Response: 返回衣物列表JSON数据
```

**如果看到错误：**
```
❌ Status: (failed)
原因：后端未启动或端口不正确
解决：确认Spring Boot已在8080端口运行
```

---

### 测试2：动态数据加载测试

**目标：** 验证前端能正确显示后端返回的数据

#### 步骤：

1. **查看浏览器控制台Console**

2. **观察日志输出**

**预期日志：**
```
🏠 页面加载完成，开始初始化...
📋 家庭信息加载成功: 我的温馨小家
✅ 页面初始化完成

(点击衣柜后)
👗 衣柜数据加载成功: 6件衣物
```

3. **检查弹窗显示的数据**

**预期结果：**
- 弹窗显示的衣物应该是从数据库读取的真实数据
- 如果数据库有图片URL，应该能看到图片

---

### 测试3：Swagger API测试

**目标：** 使用Swagger直接测试后端API

#### 步骤：

1. **打开Swagger UI**
   ```
   http://localhost:8080/swagger-ui.html
   ```

2. **展开API分组**
   - 家庭信息管理
   - 衣柜样式管理
   - 冰箱样式管理
   - 衣物管理

3. **测试接口示例**

**测试获取家庭信息：**
```
GET /api/home/info
→ Try it out → Execute
→ Response Body:
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "homeName": "我的温馨小家",
    "wardrobeStyleId": 1,
    "fridgeStyleId": 1
  }
}
```

**测试添加衣物：**
```
POST /api/clothing/add
→ Try it out → Parameters:
   name: "测试T恤"
   category: "T恤"
   brand: "UNIQLO"
   size: "L"
   color: "蓝色"
   homeId: 1
→ Execute
→ Response: 201 Created
```

---

### 测试4：跨域CORS测试

**目标：** 验证前后端跨域配置正常

#### 方法1：浏览器控制台查看

如果出现以下错误，说明CORS有问题：
```
Access to XMLHttpRequest at 'http://localhost:8080/api/...'
from origin 'http://localhost:3000' has been blocked by CORS policy
```

**解决方案：**
- 后端已配置 `CorsConfig.java`，允许所有来源
- 检查后端控制台是否有CORS相关日志

#### 方法2：使用Postman/Apifox测试

创建一个请求：
```
Method: GET
URL: http://localhost:8080/api/home/info
Headers:
  Origin: http://localhost:3000
```

检查响应头应包含：
```
Access-Control-Allow-Origin: *
```

---

## 🐛 常见问题排查

### 问题1：前端页面打不开

**症状：** 访问 `http://localhost:3000` 显示无法连接

**排查步骤：**
```bash
# 1. 检查Node.js进程是否存在
netstat -ano | findstr :3000

# 2. 如果没有，重新启动前端
node server.js

# 3. 检查3000端口是否被占用
# 如果被占用，修改server.js中的端口号
```

---

### 问题2：后端API调用失败

**症状：** 点击衣柜按钮后显示"后端服务连接失败"

**排查步骤：**

1. **检查后端是否启动**
   ```bash
   # 在后端窗口查看日志
   # 应该看到 "Started JerryFoodApplication"
   
   # 或者访问健康检查
   curl http://localhost:8080/api/health
   ```

2. **检查端口是否正确**
   ```bash
   netstat -ano | findstr :8080
   ```

3. **检查防火墙**
   - Windows Defender 可能阻止了Java程序的网络访问
   - 添加例外或临时关闭防火墙测试

4. **查看后端错误日志**
   - 在后端窗口查找红色错误信息
   - 常见错误：数据库连接失败、端口占用等

---

### 问题3：数据库连接失败

**症状：** 后端启动时报错 "Communications link failure"

**排查步骤：**

1. **确认MySQL已启动**
   ```bash
   net start mysql
   ```

2. **检查数据库配置**
   编辑 `backend/src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/jerry_food
       username: root        # 你的用户名
       password: root        # 你的密码
   ```

3. **测试数据库连接**
   ```bash
   mysql -u root -p -e "SELECT 1"
   ```

4. **确认数据库已初始化**
   ```bash
   mysql -u root -p -e "USE jerry_food; SHOW TABLES;"
   ```

---

### 问题4：图片上传失败

**症状：** 添加衣物时上传图片报错

**排查步骤：**

1. **检查上传目录是否存在**
   ```bash
   # 查看配置的上传路径
   # 默认: D:/workspace/wechat-MyLove/jerry-food/uploads/clothing/
   
   # 手动创建目录
   mkdir uploads\clothing
   ```

2. **检查目录权限**
   - 确保目录有写入权限
   - 不是只读属性

3. **检查文件大小限制**
   - 当前限制：10MB
   - 如果文件过大，会抛出 `MaxUploadSizeExceededException`

---

### 问题5：微信小程序无法访问后端

**症状：** 小程序提示"request:fail"

**解决方案：**

1. **开启不校验合法域名**
   ```
   微信开发者工具 → 详情 → 本地设置
   → 勾选 ☑️ 不校验合法域名、web-view...
   ```

2. **修改API地址为电脑IP**
   ```javascript
   // pages/index/index.js
   // 将 localhost 改为你的局域网IP
   API_BASE_URL: 'http://192.168.x.x:8080/api'
   ```

3. **确保手机和电脑在同一局域网**

---

## 📊 联调检查清单

### 启动前检查

- [ ] MySQL 服务已启动并运行在3306端口
- [ ] 数据库 `jerry_food` 已创建且包含初始数据
- [ ] JDK 1.8+ 已安装且 `JAVA_HOME` 已配置
- [ ] Maven 3.6+ 已安装且可执行
- [ ] Node.js 16+ 已安装且可执行
- [ ] `application.yml` 中数据库配置正确
- [ ] 上传目录存在且有写权限

### 启动后验证

- [ ] Spring Boot 后端启动无报错
- [ ] 访问 http://localhost:8080/api/home/info 返回200
- [ ] Swagger UI 可访问：http://localhost:8080/swagger-ui.html
- [ ] Node.js 前端启动无报错
- [ ] 访问 http://localhost:3000 显示页面
- [ ] 浏览器控制台无红色错误

### 功能测试

- [ ] 页面标题显示正确的家庭名称（从数据库读取）
- [ ] 点击衣柜弹出弹窗
- [ ] 弹窗显示数据库中的衣物列表
- [ ] 衣物有正确的分类图标
- [ ] 如果有图片URL，图片能正常显示
- [ ] 控制台显示正常的API调用日志

---

## 🔧 高级调试技巧

### 1. 查看详细请求/响应

**浏览器F12 → Network标签：**
- 点击具体请求
- 查看 Request Headers / Response Headers
- 查看 Request Payload / Response Body
- 查看Time耗时

**常用筛选：**
- Filter: `XHR` (只看AJAX请求)
- Filter: `api` (只看API请求)

---

### 2. 后端日志级别调整

编辑 `application.yml`：
```yaml
logging:
  level:
    com.jerry: debug      # 显示DEBUG级别日志
    org.springframework.web: debug  # 显示Spring Web日志
    org.hibernate.SQL: debug     # 显示SQL语句
```

重启后端生效。

---

### 3. 使用Chrome DevTools调试

在前端代码中添加断点：
```javascript
// public/index.html 的script部分
debugger;  // 在这里打断点

async function loadHomeInfo() {
    debugger;  // 断点
    const response = await fetch(...);
    ...
}
```

刷新页面后会自动暂停，可以逐步调试。

---

### 4. 性能分析

**浏览器Performance标签：**
1. 打开F12 → Performance
2. 点击录制按钮
3. 操作页面（点击衣柜等）
4. 停止录制
5. 分析时间线，找出性能瓶颈

---

## 📱 移动端联调

### 手机访问前端页面

**前提条件：**
- 手机和电脑在同一WiFi网络
- 防火墙允许3000端口访问

**步骤：**
1. **获取电脑IP地址**
   ```bash
   ipconfig
   # 找到 IPv4 地址，例如: 192.168.1.100
   ```

2. **手机浏览器访问**
   ```
   http://192.168.1.100:3000
   ```

3. **注意：**
   - API调用可能需要改为电脑IP
   - 修改 `public/index.html` 中的 `API_BASE_URL`

---

### 微信开发者工具真机预览

1. **确保后端运行正常**
2. **开启不校验域名**
3. **点击预览按钮**
4. **手机扫码查看**
5. **在手机上测试所有功能**

---

## 🎯 联调最佳实践

### 1. 先后端，后前端

```
✅ 正确顺序：
   ① 启动MySQL
   ② 启动后端，用Swagger测试所有API
   ③ 确认API都正常工作
   ④ 再启动前端，对接API
   ⑤ 测试完整流程

❌ 错误顺序：
   同时启动前后端
   → 出问题时不知道是哪边的问题
```

### 2. 使用固定测试数据

初始化SQL中已插入示例数据：
- 6件衣物
- 4种衣柜样式
- 4种冰箱样式
- 1个家庭信息

先用这些数据联调，确保基本流程跑通。

### 3. 分模块测试

不要一次性测试所有功能：
```
第1轮：测试首页加载 + 家庭信息显示
第2轮：测试衣柜弹窗 + 数据加载
第3轮：测试样式选择功能
第4轮：测试衣物增删改查
第5轮：测试图片上传
```

### 4. 保存测试场景

记录成功的测试场景，方便回归测试：
```markdown
## 测试场景1：查看衣柜
- 前置条件：数据库有6件衣物
- 操作步骤：点击衣柜按钮
- 预期结果：弹窗显示6件衣物
- 实际结果：✅ 通过
```

---

## 📝 联调日志模板

建议每次联调时记录：

```markdown
# 联调日志 - 2026-04-03

## 环境信息
- OS: Windows 10
- JDK: 1.8.0_281
- Maven: 3.6.3
- Node.js: v18.x.x
- MySQL: 8.0.x

## 启动情况
- [x] MySQL: 正常
- [x] 后端: 启动成功，耗时25s
- [x] 前端: 启动成功

## 测试结果
1. 首页加载: ✅ 通过
2. 家庭名称显示: ✅ "我的温馨小家"
3. 衣柜数据加载: ✅ 6件衣物
4. 图片显示: ⚠️ 无图片URL（正常）

## 发现问题
- 无

## 下一步计划
- 测试添加衣物功能
- 测试图片上传
```

---

## ✅ 联调完成标准

当满足以下条件时，可以认为联调通过：

1. ✅ 所有服务正常启动（MySQL + 后端 + 前端）
2. ✅ 前端页面可以正常访问（http://localhost:3000）
3. ✅ 后端API文档可访问（http://localhost:8080/swagger-ui.html）
4. ✅ 前端能成功调用后端API（无CORS错误）
5. ✅ 数据能正确显示（从数据库读取）
6. ✅ 基本交互流程正常（点击、弹窗、关闭）
7. ✅ 控制台无严重错误

---

## 💡 提示与技巧

### 开发效率提升

1. **保持后端窗口可见**
   - 方便实时查看日志
   - 及时发现错误

2. **使用浏览器书签**
   - 收藏常用URL：
     - 前端页面
     - Swagger UI
     - H2 Console（如果有）

3. **善用IDE快捷键**
   - IDEA: Shift+F9 调试
   - VSCode: F5 刷新
   - Chrome: F12 开发者工具

4. **版本控制**
   - 联调前提交代码
   - 出问题时方便回滚

---

**文档版本**: v1.0  
**最后更新**: 2026-04-03  
**适用阶段**: 开发联调、集成测试