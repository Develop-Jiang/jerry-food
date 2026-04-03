# Jerry Food 后端服务 - Spring Cloud 微服务

## 📋 项目概述

这是一个基于 Spring Boot 2.7 + Spring Cloud 的后端微服务，为"温馨小家"微信小程序提供完整的后端支持。

## ✨ 功能特性

### 1. 家庭信息管理
- ✅ 获取家庭信息（名称、样式配置）
- ✅ 更改家庭名称
- ✅ 选择衣柜和冰箱样式

### 2. 样式管理
- ✅ 衣柜样式选择（4种预设样式）
- ✅ 冰箱样式选择（4种预设样式）
- ✅ 支持自定义样式扩展

### 3. 衣物管理
- ✅ 衣物CRUD操作
- ✅ 图片上传功能（支持10MB以内）
- ✅ 衣物分类管理
- ✅ 收藏功能
- ✅ 按季节/分类筛选

## 🗄️ 数据库设计

### 表结构
- `home_info` - 家庭信息表
- `wardrobe_style` - 衣柜样式表
- `fridge_style` - 冰箱样式表
- `clothing` - 衣物表

### 数据库要求
- MySQL 8.0+
- 数据库名：`jerry_food`
- 字符集：`utf8mb4`

## 🚀 快速开始

### 1. 环境准备

**必需软件：**
- JDK 1.8+ (Java 8)
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库初始化

```bash
# 登录MySQL
mysql -u root -p

# 执行初始化脚本
source backend/src/main/resources/db/init.sql
```

### 3. 配置数据库连接

编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jerry_food?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root          # 你的MySQL用户名
    password: root         # 你的MySQL密码
```

### 4. 启动项目

```bash
cd backend

# 使用Maven启动
mvn spring-boot:run

# 或者打包后运行
mvn clean package -DskipTests
java -jar target/jerry-food-backend-1.0.0.jar
```

### 5. 访问服务

服务启动成功后：

- **API基础地址**: http://localhost:8080/api
- **Swagger文档**: http://localhost:8080/swagger-ui.html

## 📡 API接口文档

### 家庭信息接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/home/info` | 获取家庭信息 |
| PUT | `/home/{id}/name` | 更新家庭名称 |
| PUT | `/home/{id}/styles` | 更新衣柜和冰箱样式 |

### 衣柜样式接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/wardrobe/styles` | 获取所有衣柜样式 |
| GET | `/wardrobe/styles/{id}` | 根据ID获取样式 |
| GET | `/wardrobe/default-style` | 获取默认样式 |

### 冰箱样式接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/fridge/styles` | 获取所有冰箱样式 |
| GET | `/fridge/styles/{id}` | 根据ID获取样式 |
| GET | `/fridge/default-style` | 获取默认样式 |

### 衣物管理接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/clothing/list` | 获取所有衣物 |
| GET | `/clothing/category` | 按分类获取衣物 |
| GET | `/clothing/favorite` | 获取收藏的衣物 |
| POST | `/clothing/add` | 添加衣物（支持图片上传） |
| PUT | `/clothing/{id}` | 更新衣物信息 |
| DELETE | `/clothing/{id}` | 删除衣物 |
| PUT | `/clothing/{id}/favorite` | 切换收藏状态 |

## 📁 项目结构

```
backend/
├── pom.xml                          # Maven配置文件
├── src/
│   ├── main/
│   │   ├── java/com/jerry/
│   │   │   ├── JerryFoodApplication.java      # 启动类
│   │   │   ├── common/
│   │   │   │   └── Result.java               # 统一响应结果
│   │   │   ├── config/
│   │   │   ├── CorsConfig.java              # CORS跨域配置
│   │   │   ├── SwaggerConfig.java           # Swagger配置
│   │   │   └── WebConfig.java               # Web配置
│   │   │   ├── controller/
│   │   │   ├── HomeController.java          # 家庭信息控制器
│   │   │   ├── WardrobeStyleController.java # 衣柜样式控制器
│   │   │   ├── FridgeStyleController.java   # 冰箱样式控制器
│   │   │   └── ClothingController.java      # 衣物管理控制器
│   │   │   ├── entity/
│   │   │   ├── HomeInfo.java                # 家庭信息实体
│   │   │   ├── WardrobeStyle.java           # 衣柜样式实体
│   │   │   ├── FridgeStyle.java             # 冰箱样式实体
│   │   │   └── Clothing.java                # 衣物实体
│   │   │   ├── exception/
│   │   │   └── GlobalExceptionHandler.java  # 全局异常处理
│   │   │   ├── repository/
│   │   │   ├── HomeInfoRepository.java      # 家庭信息仓库
│   │   │   ├── WardrobeStyleRepository.java # 衣柜样式仓库
│   │   │   ├── FridgeStyleRepository.java   # 冰箱样式仓库
│   │   │   └── ClothingRepository.java      # 衣物仓库
│   │   │   └── service/
│   │   │       ├── HomeInfoService.java     # 家庭信息服务
│   │   │       ├── WardrobeStyleService.java# 衣柜样式服务
│   │   │       ├── FridgeStyleService.java  # 冰箱样式服务
│   │   │       └── ClothingService.java     # 衣物服务
│   │   └── resources/
│   │       ├── application.yml             # 应用配置
│   │       └── db/
│   │           └── init.sql                 # 数据库初始化脚本
└── README.md                        # 项目说明文档
```

## 🔧 配置说明

### application.yml 主要配置项

```yaml
server:
  port: 8080                    # 服务端口
  
spring:
  datasource:
    url: jdbc:mysql://...       # 数据库连接URL
    username: root              # 数据库用户名
    password: root              # 数据库密码
    
  servlet:
    multipart:
      max-file-size: 10MB      # 单文件最大大小
      max-request-size: 10MB   # 请求最大大小
      
file:
  upload:
    path: D:/uploads/          # 文件上传路径
```

## 🖼️ 图片上传

- **支持格式**: jpg, jpeg, png, gif
- **文件大小**: 最大10MB
- **存储位置**: `{upload.path}/clothing/`
- **访问路径**: `/uploads/clothing/{filename}`

## 🔐 安全建议

⚠️ **生产环境注意事项：**

1. **修改默认密码**: 更改数据库密码
2. **启用HTTPS**: 配置SSL证书
3. **限制CORS**: 不要使用通配符*
4. **添加认证**: 集成Spring Security或JWT
5. **日志安全**: 不要记录敏感信息

## 🛠️ 技术栈

- **框架**: Spring Boot 2.7.18
- **ORM**: Spring Data JPA / Hibernate
- **数据库**: MySQL 8.0
- **API文档**: Swagger 2.9.2
- **工具库**: Lombok
- **构建工具**: Maven

## 📝 开发指南

### 添加新的衣物分类

1. 在数据库中添加分类数据
2. 前端调用 `/clothing/category?category=xxx` 接口

### 自定义家具样式

1. 在 `wardrobe_style` 或 `fridge_style` 表中插入新记录
2. 设置 `is_default=1` 可设为默认样式

### 扩展衣物属性

1. 修改 `Clothing` 实体类，添加新字段
2. JPA会自动更新数据库表结构
3. 在Controller中暴露新字段

## 🐛 常见问题

### Q: 启动时数据库连接失败？
A: 检查MySQL是否启动，确认application.yml中的数据库配置正确

### Q: 图片上传失败？
A: 确保上传目录存在且有写权限，检查文件大小是否超过限制

### Q: CORS跨域问题？
A: 已在CorsConfig中配置了全局跨域支持

## 📞 技术支持

如有问题，请查看：
1. 控制台日志输出
2. Swagger API文档：http://localhost:8080/swagger-ui.html
3. MySQL错误日志

## 📄 版本历史

- **v1.0.0** (2026-04-03)
  - 初始版本发布
  - 实现核心功能
  - 支持图片上传