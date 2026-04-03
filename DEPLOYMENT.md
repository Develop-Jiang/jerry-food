# 🚀 Jerry Food 完整项目部署指南

## 📦 项目组成

本项目包含三个部分：
1. **前端小程序** - 微信小程序 + HTML预览页面
2. **后端服务** - Spring Boot微服务
3. **数据库** - MySQL 8.0

---

## 🎯 快速开始（5分钟部署）

### 第一步：环境准备 ✅

确保已安装以下软件：

| 软件 | 版本要求 | 用途 |
|------|---------|------|
| JDK | 1.8+ (Java 8) | 运行Java后端 |
| Maven | 3.6+ | 构建项目 |
| MySQL | 8.0+ | 数据存储 |
| Node.js | 16+ | 前端服务（可选） |

### 第二步：初始化数据库 📊

1. 打开MySQL命令行或Navicat/DBeaver等工具
2. 执行初始化脚本：

```bash
# 方式1：使用批处理脚本（推荐）
cd backend
双击运行 init-db.bat

# 方式2：手动执行SQL
mysql -u root -p < backend/src/main/resources/db/init.sql
```

3. 验证数据库创建成功：
   - 数据库名：`jerry_food`
   - 包含4张表：`home_info`, `wardrobe_style`, `fridge_style`, `clothing`

### 第三步：配置数据库连接 ⚙️

编辑文件：`backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jerry_food?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root      # 改成你的用户名
    password: root      # 改成你的密码
```

### 第四步：启动后端服务 🚀

```bash
# 方式1：使用启动脚本（推荐）
cd backend
双击运行 start.bat

# 方式2：使用Maven
cd backend
mvn spring-boot:run

# 方式3：打包后运行
cd backend
mvn clean package -DskipTests
java -jar target/jerry-food-backend-1.0.0.jar
```

看到以下输出表示启动成功：

```
============================================
   🏠 Jerry Food Backend Service Started!  
   📍 http://localhost:8080/api            
   📚 Swagger: http://localhost:8080/swagger-ui.html
============================================
```

### 第五步：测试API接口 🧪

打开浏览器访问：http://localhost:8080/swagger-ui.html

测试接口示例：

1. **获取家庭信息**
   - GET `/api/home/info`
   
2. **获取衣柜样式列表**
   - GET `/api/wardrobe/styles`
   
3. **添加衣物（带图片）**
   - POST `/api/clothing/add`
   - 参数：name, category, brand, size, color, imageFile(文件)

---

## 📱 前端对接指南

### 方式A：微信小程序开发工具

1. 打开微信开发者工具
2. 导入项目目录：`jerry-food/`
3. 修改请求地址为后端地址：
   ```javascript
   // pages/index/index.js
   const BASE_URL = 'http://localhost:8080/api'
   ```
4. 开启「不校验合法域名」选项
5. 点击编译，查看效果

### 方式B：浏览器直接访问

1. 确保Node.js服务器运行中（端口3000）
2. 访问：http://localhost:3000
3. 或直接访问HTML文件：`public/index.html`

### 方式C：对接Spring Boot后端

修改前端代码，将API调用指向Spring Boot：

```javascript
// 示例：获取衣物列表
wx.request({
  url: 'http://localhost:8080/api/clothing/list?homeId=1',
  method: 'GET',
  success(res) {
    console.log(res.data);
  }
});
```

---

## 🎨 功能演示流程

### 1. 更改家庭名称

```bash
# API调用
PUT http://localhost:8080/api/home/1/name?homeName=我的温馨小家
```

### 2. 选择家具样式

```bash
# 查看可用样式
GET http://localhost:8080/api/wardrobe/styles
GET http://localhost:8080/api/fridge/styles

# 更新样式（假设选择了ID=2的样式）
PUT http://localhost:8080/api/home/1/styles?wardrobeStyleId=2&fridgeStyleId=2
```

### 3. 添加衣物并上传图片

使用Postman或Swagger UI测试：

```
POST /api/clothing/add
Content-Type: multipart/form-data

参数：
- name: 白色衬衫
- category: 衬衫
- brand: UNIQLO
- size: M
- color: 白色
- season: 春夏
- material: 棉质
- price: 199.00
- homeId: 1
- wardrobePosition: 1
- imageFile: [选择图片文件]
```

### 4. 管理衣物

```bash
# 获取所有衣物
GET /api/clothing/list?homeId=1

# 获取衬衫分类
GET /api/clothing/category?homeId=1&category=衬衫

# 收藏衣物
PUT /api/clothing/1/favorite

# 删除衣物
DELETE /api/clothing/1
```

---

## 🔧 常见问题解决

### Q1: 启动报错 "Table doesn't exist"

**解决方案：**
```bash
# 重新执行数据库初始化脚本
cd backend
init-db.bat
```

### Q2: 图片上传失败

**检查项：**
1. 上传目录是否存在：`D:/workspace/wechat-MyLove/jerry-food/uploads/clothing/`
2. 文件大小是否超过10MB
3. 目录是否有写入权限

**手动创建目录：**
```bash
mkdir uploads\clothing
```

### Q3: 前端无法连接后端

**检查项：**
1. 后端是否启动成功（访问 http://localhost:8080/api/home/info）
2. CORS是否配置正确（已默认配置）
3. 小程序是否开启「不校验合法域名」

### Q4: MySQL连接失败

**排查步骤：**
1. 确认MySQL服务已启动
2. 检查用户名密码是否正确
3. 确认端口3306未被占用
4. 检查防火墙设置

---

## 📁 项目文件结构总览

```
jerry-food/
├── backend/                          # Spring Boot后端
│   ├── pom.xml                      # Maven配置
│   ├── start.bat                    # 启动脚本
│   ├── init-db.bat                  # 数据库初始化脚本
│   ├── README.md                    # 后端文档
│   └── src/
│       └── main/
│           ├── java/com/jerry/     # Java源码
│           │   ├── controller/     # 控制器层
│           │   ├── service/        # 业务逻辑层
│           │   ├── repository/     # 数据访问层
│           │   ├── entity/         # 实体类
│           │   ├── common/         # 公共类
│           │   ├── config/         # 配置类
│           │   └── exception/      # 异常处理
│           └── resources/
│               ├── application.yml # 配置文件
│               └── db/init.sql     # 初始数据
│
├── public/                          # 前端静态资源
│   └── index.html                  # 浏览器预览页面
│
├── pages/                           # 微信小程序页面
│   └── index/                      # 首页
│       ├── index.wxml              # 页面结构
│       ├── index.js                # 页面逻辑
│       └── index.wxss              # 页面样式
│
├── app.json                        # 小程序配置
├── app.js                          # 小程序入口
├── server.js                       # Node.js简单服务器
├── package.json                    # Node.js依赖
└── README.md                       # 项目说明
```

---

## 🌐 生产环境部署建议

### 1. 数据库优化
```sql
-- 为常用查询添加索引
CREATE INDEX idx_clothing_home_id ON clothing(home_id);
CREATE INDEX idx_clothing_category ON clothing(category);
CREATE INDEX idx_clothing_favorite ON clothing(favorite);
```

### 2. 安全加固
- 修改application.yml中的数据库密码
- 启用HTTPS
- 配置Spring Security JWT认证
- 限制CORS来源

### 3. 性能优化
- 配置Redis缓存
- 使用Nginx反向代理
- 开启Gzip压缩
- 配置CDN加速图片

### 4. 监控告警
- 集成Prometheus监控
- 配置日志收集（ELK）
- 设置健康检查接口

---

## 📞 技术支持

遇到问题？请按顺序检查：

1. ✅ 查看控制台错误日志
2. ✅ 访问 Swagger 文档测试API
3. ✅ 检查数据库连接状态
4. ✅ 确认所有服务都已启动

**相关链接：**
- 后端API文档：http://localhost:8080/swagger-ui.html
- MySQL官方文档：https://dev.mysql.com/doc/
- Spring Boot文档：https://docs.spring.io/spring-boot/docs/current/reference/html/

---

## ✅ 部署清单

部署前请确认：

- [ ] JDK 1.8+ (Java 8) 已安装
- [ ] Maven 3.6+ 已安装  
- [ ] MySQL 8.0+ 已安装并启动
- [ ] 数据库已初始化（执行init.sql）
- [ ] application.yml配置已修改
- [ ] 上传目录已创建并有写权限
- [ ] 防火墙允许8080端口访问

全部完成后，即可享受完整的"温馨小家"应用！🏠✨