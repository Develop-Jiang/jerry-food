# Jerry Food 项目 - 开发环境配置文档

## 📋 你的开发环境信息（已确认）

### Java 环境
- **版本**: JDK 1.8.0_281 (Oracle Corporation)
- **安装路径**: `D:\develop\jdk1.8.0_281\jre`
- **状态**: ✅ 已确认

### Maven 环境
- **版本**: Apache Maven 3.6.3
- **安装路径**: `D:\develop\apache-maven-3.6.3`
- **配置文件**: `D:\develop\apache-maven-3.6.3\conf\settings.xml`
- **本地仓库**: `D:\develop\mvn_repo`
- **状态**: ✅ 已确认

---

## 🗄️ Maven 仓库配置详解

### 你的 settings.xml 配置位置
```
D:\develop\apache-maven-3.6.3\conf\settings.xml
```

### 当前配置的仓库

#### 1️⃣ 公司 Nexus 私服（主仓库）
```xml
<mirror>
    <id>asiainfo-osc</id>
    <mirrorOf>center</mirrorOf>
    <url>http://10.19.83.146:8081/nexus/repository/maven-group/</url>
</mirror>
```

**使用场景：**
- ✅ 在公司内网时优先使用
- ⚠️ 在外网时可能无法访问

#### 2️⃣ pom.xml 中配置的备用仓库（本项目新增）

| 仓库ID | 名称 | URL | 作用 |
|--------|------|-----|------|
| `aliyunmaven` | 阿里云公共仓库 | `https://maven.aliyun.com/repository/public` | 备用仓库 #1 - 国内高速 |
| `spring-milestones` | Spring官方仓库 | `https://repo.spring.io/milestone` | 备用仓库 #2 - Spring依赖 |
| `central` | Maven中央仓库 | `https://repo.maven.apache.org/maven2` | 兜底仓库 |

---

## 🔄 混合模式工作原理

### 构建流程图

```
开始构建 (mvn spring-boot:run)
    ↓
检查网络环境
    ↓
┌─────────────────────────────────────┐
│  在公司内网？                        │
│  ┌──────────────┐                   │
│  │ YES → 使用公司Nexus私服          │
│  │     http://10.19.83.146:8081   │
│  └──────────────┘                   │
│                                     │
│  ┌──────────────┐                   │
│  │ NO  → 使用备用仓库               │
│  │   ① 阿里云公共仓库               │
│  │   ② Spring官方仓库              │
│  │   ③ Maven中央仓库（兜底）        │
│  └──────────────┘                   │
└─────────────────────────────────────┘
    ↓
下载依赖到本地仓库：D:\develop\mvn_repo\
    ↓
编译并启动项目 ✅
```

### 依赖查找顺序

当 Maven 需要下载依赖时，会按以下顺序查找：

1. **本地仓库** (`D:\develop\mvn_repo`)
   - 如果已有缓存，直接使用 ✅
   
2. **公司Nexus私服** (如果可访问)
   - 地址：`http://10.19.83.146:8081/...`
   
3. **阿里云公共仓库**
   - 地址：`https://maven.aliyun.com/repository/public`
   
4. **Spring官方仓库**
   - 仅用于Spring相关依赖
   
5. **Maven中央仓库**
   - 最终兜底方案

---

## 🚀 如何构建项目

### 方式1：在公司内网（推荐）

直接运行，Maven会自动使用公司Nexus私服：

```bash
cd D:\workspace\wechat-MyLove\jerry-food\backend

# 启动项目
mvn spring-boot:run
```

**优势：**
- ⚡ 速度快（内网传输）
- 🔒 安全（公司认证）
- ✅ 稳定（内部维护）

---

### 方式2：在外网/家里

同样运行命令，Maven会自动切换到阿里云仓库：

```bash
cd D:\workspace\wechat-MyLove\jerry-food\backend

# 启动项目
mvn spring-boot:run
```

**工作原理：**
1. 尝试连接公司Nexus → ❌ 超时/失败
2. 自动降级到阿里云仓库 → ✅ 成功下载
3. 缓存到本地仓库 → 下次更快

---

### 方式3：强制清理后重建

如果想重新下载所有依赖：

```bash
cd backend

# 清理本地缓存（谨慎使用）
mvn clean

# 强制更新依赖（-U参数）
mvn dependency:resolve -U

# 编译项目
mvn compile

# 运行项目
mvn spring-boot:run
```

---

## 📁 重要路径汇总

### Maven 相关路径

| 用途 | 路径 |
|------|------|
| **Maven主目录** | `D:\develop\apache-maven-3.6.3` |
| **全局settings.xml** | `D:\develop\apache-maven-3.6.3\conf\settings.xml` |
| **本地仓库** | `D:\develop\mvn_repo` |
| **用户级settings.xml** | `C:\Users\PC\.m2\settings.xml` (不存在) |

### 项目相关路径

| 用途 | 路径 |
|------|------|
| **项目根目录** | `D:\workspace\wechat-MyLove\jerry-food` |
| **后端代码** | `D:\workspace\wechat-MyLove\jerry-food\backend` |
| **pom.xml** | `D:\workspace\wechat-MyLove\jerry-food\backend\pom.xml` |
| **上传文件存储** | `D:\workspace\wechat-MyLove\jerry-food\uploads\clothing\` |

### Java 相关路径

| 用途 | 路径 |
|------|------|
| **JDK主目录** | `D:\develop\jdk1.8.0_281` |
| **JRE运行时** | `D:\develop\jdk1.8.0_281\jre` |

---

## ⚙️ IDE 配置建议

### IntelliJ IDEA 配置

1. **设置JDK路径**
   ```
   File → Project Structure → SDKs
   → Add SDK → 选择: D:\develop\jdk1.8.0_281
   ```

2. **设置Maven路径**
   ```
   File → Settings → Build, Execution, Deployment → Build Tools → Maven
   → Maven home directory: D:\develop\apache-maven-3.6.3
   → User settings file: D:\develop\apache-maven-3.6.3\conf\settings.xml
   → Local repository: D:\develop\mvn_repo
   ```

3. **设置Java Compiler**
   ```
   File → Settings → Build, Execution, Deployment → Compiler → Java Compiler
   → Project bytecode version: 1.8
   → Target bytecode version: 1.8
   ```

### Eclipse 配置

1. **设置JDK**
   ```
   Window → Preferences → Java → Installed JREs
   → Add → Standard VM → Directory: D:\develop\jdk1.8.0_281
   ```

2. **设置Maven**
   ```
   Window → Preferences → Maven → Installations
   → Add → Directory: D:\develop\apache-maven-3.6.3
   
   Window → Preferences → Maven → User Settings
   → User Settings: D:\develop\apache-maven-3.6.3\conf\settings.xml
   → Local Repository: D:\develop\mvn_repo
   ```

---

## 🔧 常见问题解决

### Q1: 在外网构建时提示连接公司Nexus超时？

**正常现象！** 这是预期行为。

**解决方案：**
- 等待超时（约30秒），Maven会自动切换到阿里云仓库
- 或者临时修改settings.xml注释掉mirror配置

### Q2: 想只使用阿里云，不用公司私服？

**方法：** 创建用户级settings.xml覆盖全局配置

```bash
# 创建文件
C:\Users\PC\.m2\settings.xml

# 内容如下：
<?xml version="1.0" encoding="UTF-8"?>
<settings>
    <mirrors>
        <mirror>
            <id>aliyunmaven</id>
            <mirrorOf>*</mirrorOf>
            <name>阿里云公共仓库</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </mirror>
    </mirrors>
</settings>
```

**注意：** 用户级settings.xml会**覆盖**全局配置！

### Q3: 依赖下载失败或损坏？

```bash
# 删除特定依赖的缓存
rd /s /q "D:\develop\mvn_repo\org\springframework\boot"

# 重新下载
mvn dependency:resolve -U
```

### Q4: 查看Maven实际使用的仓库？

```bash
# 开启调试模式
mvn spring-boot:run -X

# 在输出中搜索 "repository" 关键字
# 可以看到实际访问的仓库URL
```

### Q5: 本地仓库占用空间太大？

```bash
# 查看仓库大小
du -sh D:\develop\mvn_repo

# 清理未使用的依赖（谨慎操作）
mvn dependency:purge-local-repository
```

---

## 📊 环境验证清单

在开始开发前，请确认以下项：

### Java 环境
- [ ] `java -version` 显示 1.8.0_281
- [ ] `JAVA_HOME` 环境变量指向 `D:\develop\jdk1.8.0_281`
- [ ] IDE中配置了正确的JDK路径

### Maven 环境
- [ ] `mvn -version` 显示 3.6.3
- [ ] Maven home 正确指向 `D:\develop\apache-maven-3.6.3`
- [ ] 本地仓库路径为 `D:\develop\mvn_repo`
- [ ] settings.xml 文件存在且格式正确

### 网络连接
- [ ] 在公司内网时可访问 `http://10.19.83.146:8081`
- [ ] 在外网时可访问 `https://maven.aliyun.com`
- [ ] 防火墙允许Maven下载文件

### 项目配置
- [ ] pom.xml 中的Java版本为1.8
- [ ] application.yml中的数据库配置正确
- [ ] 上传目录存在且有写权限

---

## 📝 版本历史

- **2026-04-03**: 初始化文档
  - 记录用户的实际开发环境
  - 说明混合模式的仓库配置
  - 提供详细的故障排除指南

---

## 💡 最佳实践建议

1. **不要频繁修改settings.xml** - 全局配置影响所有项目
2. **项目特定配置放pom.xml** - 只影响当前项目
3. **定期清理本地仓库** - 释放磁盘空间
4. **使用IDE的Maven集成** - 提高开发效率
5. **保持Java和Maven版本稳定** - 避免兼容性问题

---

**文档维护人**: AI Assistant  
**最后更新**: 2026-04-03  
**适用项目**: Jerry Food Backend Service