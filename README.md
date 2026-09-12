# 生鲜商品管理系统 - 后端

基于 Spring Boot 与 MyBatis-Plus 的生鲜商品管理系统后端，面向用户、商家和管理端提供商品、库存、订单及基础运营接口。

## 主要功能

- 用户与商家管理
- 商品分类与商品信息管理
- 库存入库、出库与库存状态管理
- 购物车、收货地址、订单与充值记录
- 商品收藏、评论与资讯管理
- 文件上传、系统配置及数据统计接口

## 技术栈

- Java 21
- Spring Boot 2.7.18
- MyBatis、MyBatis-Plus
- MySQL 8
- Apache Shiro
- Maven
- 百度 AI SDK

## 目录结构

```text
src/main/java              Java 源码
src/main/resources/mapper  MyBatis XML
src/main/resources/front   用户端静态资源
database/schema.sql        数据库表结构
database/migrations        数据库增量脚本
```

## 本地运行

1. 准备 JDK 21、Maven 3.8+、MySQL 8。
2. 创建数据库并导入 `database/schema.sql`。
3. 为保护数据，本仓库未包含原始业务数据和用户数据，首次运行需要自行创建测试账号及基础数据。
4. 设置数据库密码环境变量：

```powershell
$env:DB_PASSWORD = "你的本地数据库密码"
```

5. 在项目根目录运行：

```powershell
.\mvnw.cmd spring-boot:run
```

默认服务端口为 `8890`，接口前缀为 `/springbootcug86`。

## 安全说明

- 数据库密码通过 `DB_PASSWORD` 环境变量注入。
- 不要提交 `.env`、本地配置文件、生产密钥、用户上传文件或包含个人信息的数据库导出。
- 原始数据库导出包含演示用户和地址数据，因此公开仓库仅保留表结构。