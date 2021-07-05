# Spring Cloud Gateway Dubbo
# 工程简介
Spring Cloud Gateway Dubbo 基于Spring Cloud Alibaba的SpringCloud集成Dubbo的方案之上开发，
用于在SpringCloudGateway中直接调用Dubbo接口，可以减少使用Web接口来实现与gateway对接。
## 架构
TODO 待补充
## 已实现功能
* Dubbo路由解析及转发
* Dubbo Consumer缓存
* Dubbo 参数rewrite
    - 内置header,query,cookie,body变量
    - 支持自定义变量扩展
    - 内置Spel,Velocity引擎渲染变量
* Dubbo调用结果转换
# 工程结构
|  模块   | 描述  |
|  ----  | ----  |
| spring-cloud-gateway-dubbo  | 集成Dubbo调用功能，包括路由解析、转发、参数处理、参数重写、结果转换等功能 |
| spring-cloud-gateway-dubbo-starter  | 集成dubbo功能自动装配 |
| spring-cloud-gateway-dubbo-sample  | 集成Dubbo调用示例 |

# 示例工程
## 工程结构
|  模块   | 描述  |
|  ----  | ----  |
| dubbo-provider | Dubbo服务提供方 |
| gateway  | 网关 |
## 依赖
* 注册中心使用nacos，用于Dubbo服务注册，需要本地启动Nacos，如果非本地Nacos则需求修改配置，配置文件: **spring-cloud-gateway-dubbo-sample/gateway/src/main/resources/application.yml**

## 启动
分别启动：
```java
com.github.leecho.spring.cloud.dubbo.sample.gateway.GatewayApplication
com.github.leecho.spring.cloud.dubbo.sample.provider.DubboProviderApplication
```
## 测试
在idea中执行http文件：gateway.http

## 配置dubbo路由
在Gateway中定义路由
```
- id: echo
  uri: dubbo://dubbo-provider/EchoService/echo
  predicates:
  - Path=/echo/**
  metadata:
    dubbo:
      group: default
      version: 1.0
      parameterTypes:
        - java.lang.String
```
dubbo路由uri结构：dubbo://{serviceName}/{interfaceClass}/{method}，dubbo接口其他属性在metadata中进行配置，内置的解析器会识别scheme为dubbo的uri，也可以进行自定义

## 重写dubbo参数
在Gateway中定义路由
```
- id: rewrite
  uri: dubbo://dubbo-provider/EchoService/request
  metadata:
    dubbo:
      group: default
      version: 1.0
      parameterTypes:
        - Request
        - Action
      rewrite:
        request:
          message: "#body['request']['message']"
          target:
            name: "#cookie['JSESSIONID']"
            id: "#header['Authorization']"
          date: 2021-07-01 00:00:00
          #attributes: "#body['request']['attributes']"
          attributes:
            - "#header['Authorization']"
            - attr2
            - attr3
        action:
          name: request
          parameters:
            param1: "#auth['user']"
  predicates:
    - Path=/rewrite/**
```
## 如何扩展
### 将Dubbo结果转成成自定义结构
配置DubboMessageConverter对象，会自动识别并进行装配：
```
@Bean
public DubboMessageConverter dubboResultConverter() {
    return source -> {
    Map<String, Object> result = new HashMap<>(2);
    result.put("success", true);
    result.put("data", source);
    return result;
};
```
### 自定义Consumer缓存
配置DubboGenericServiceCache对象，会自动识别并进行装配：

```
@Bean
public DubboGenericServiceCache dubboGenericServiceCache() {
    return new GuavaDubboGenericServiceCache(1000, Duration.of(10, ChronoUnit.SECONDS));
}
```

### 扩展自定义变量
配置VariableLoader对象

```
	@Bean
	public VariableLoader authVariableProcessor() {
		return context -> {
			Map<String, Object> auth = new HashMap<>();
			auth.put("user", "user");
			context.setVariable("auth", auth);
		};
	}

```

## 已知问题
* 配置Dubbo重写参数时，参数类型为list时geteway路由会解析成map
* 尚未经过大规模测试，在生产环境上使用请先进行测试，欢迎大家测试后提供测试结果及改进意见

## 交流
欢迎大家进行使用及测试，有任何问题可以在平台上提交issue或者站内信联系我，也欢迎大家提交pr，共同为开源社区提供更好的方案。