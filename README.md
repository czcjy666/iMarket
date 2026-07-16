# iMarket - 微服务电商系统

基于 Spring Cloud Alibaba 的电商微服务项目，采用服务拆分架构，涵盖商品、购物车、交易、支付、用户等核心业务模块。

## 技术栈

| 技术 | 说明 |
|------|------|
| Spring Boot 2.7 | 基础框架 |
| Spring Cloud 2021 | 微服务生态 |
| Spring Cloud Alibaba 2021 | 微服务增强（Nacos / Seata） |
| MyBatis-Plus 3.4 | ORM 持久层 |
| MySQL 8.0 | 关系型数据库 |
| RabbitMQ | 消息队列 |
| JDK 11 | 运行环境 |

## 项目结构

```
iMarket
├── imarket-common      # 公共模块（工具类、通用配置、异常处理）
├── imarket-api          # 远程调用接口（Feign Client）
├── imarket-gateway      # API 网关（路由、鉴权、JWT 校验）
├── item-service         # 商品服务
├── cart-service         # 购物车服务
├── trade-service        # 交易服务（订单管理）
├── pay-service          # 支付服务
└── user-service         # 用户服务（登录、地址管理）
```

## 核心功能

- **服务注册与发现** — 基于 Nacos 实现微服务自动注册与发现
- **API 网关** — 统一入口路由、全局 JWT 鉴权、请求过滤
- **远程调用** — 基于 OpenFeign 实现服务间通信，配合 LoadBalancer 负载均衡
- **分布式事务** — 基于 Seata AT 模式保证跨服务数据一致性
- **异步通信** — 基于 RabbitMQ 实现服务间解耦（支付回调通知交易服务）
- **JWT 认证** — 用户登录签发 Token，网关层统一校验

## 环境要求

- JDK 11+
- Maven 3.6+
- MySQL 8.0
- Nacos Server
- RabbitMQ
- Seata Server

## 快速开始

1. 启动基础设施：MySQL、Nacos、RabbitMQ、Seata
2. 创建数据库：`imarket-item`、`imarket-cart`、`imarket-trade`、`imarket-pay`、`imarket-user`
3. 修改各服务 `application-local.yaml` 中的数据库连接配置
4. 按顺序启动服务：
   - `imarket-common` → `imarket-api`（先 install）
   - `imarket-gateway`（端口 8080）
   - `item-service`（端口 8081）
   - `cart-service`（端口 8082）
   - `user-service`（端口 8084）
   - `trade-service`（端口 8085）
   - `pay-service`（端口 8086）

## 端口一览

| 服务 | 端口 |
|------|------|
| Gateway | 8080 |
| Item Service | 8081 |
| Cart Service | 8082 |
| User Service | 8084 |
| Trade Service | 8085 |
| Pay Service | 8086 |
