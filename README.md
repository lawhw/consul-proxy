## Consul-proxy项目简介

Springcloud+consul作为微服务的注册已经见怪不怪了，试下也很流行，在我个人云服务器上，我也是这样做的。

然而，我的云服务器内存比较小，很快内存就被cloud全家桶吃光了，没办法部署其他应用了，因此，我觉得将一些服务独立出去，放弃cloud全家桶。

Consul-proxy使用netty+consul实现服务注册，并提供了若干简单的注解实现了http的mapping映射处理。

## [Gitee](https://gitee.com/ffch/consul-proxy)
## [Github](https://github.com/ffch/consul-proxy)


## 主要功能

 1. 快速启动。
 2. 映射路径。
 3. handler中的属性注入。
 

## 使用说明

### 启动

使用注解@JsonServer启动，可以指定端口和handler处理逻辑。

```java
@JsonServer(handler=AlarmHandler.class)
public class AlarmApp {
	public static void main(String[] args) {
		ConsulProxyApplication.run(AlarmApp.class);
	}

}
```

### 业务逻辑

继承AbstractResourceHandler的handler可以实现业务逻辑。

#### 属性注入

handler中可以使用Value注解进行属性注入：

```java
@Value("api.gateway.kongUrl")
private String apiGatewayKongUrl;
```

#### 路径映射

handler中可以使用Mapping注解进行路径映射：

```java
@Mapping("/alarm/gateway")
```

## Demo项目

## [Gitee-Consul-proxy-test](https://gitee.com/ffch/consul-proxy-test)
## [Github-Consul-proxy-test](https://github.com/ffch/consul-proxy-test)

## 版权声明
JpaMapper使用 Apache License 2.0 协议.

## 作者信息
      
   作者博客：https://blog.csdn.net/feiyangtianyao
  
  个人网站：https://www.pomit.cn
 
   作者邮箱： fufeixiaoyu@163.com

## License
Apache License V2
