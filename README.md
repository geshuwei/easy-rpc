
###功能
在spring cloud基础上搭建的rpc框架

###使用方法
服务提供方 ：  
1、引入依赖
```$xslt
<dependency>
    <artifactId>server</artifactId>
    <groupId>com.gsw.easyrpc</groupId>
    <version>${version}</version>
</dependency>
```
2、使用` @Import(EasyRpcServerAutoConfiguration.class)`注解引入配置  
3、定义一个接口继承`EasyRpcService`接口。
4、实现这个接口，实现类必须是管理的bean，不然会找不到服务。  
  
*example：*
```$xslt
@SpringBootApplication
@EnableDiscoveryClient
@Import(EasyRpcServerAutoConfiguration.class)
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class);
    }
}

```
服务消费方：  
1、引入依赖
```$xslt
<dependency>
    <groupId>com.gsw.easyrpc</groupId>
    <artifactId>client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2、使用 `@EasyRpcClient（remoteServerName = "${serviceName}"）`注解标注服务提供方提供的接口，并指明服务方的服务名称（服务提供方需要提供一个接口包供服务消费方使用）。  
*example*

```$xslt
public class ClientController {


    @EasyRpcClient(remoteServerName = "easy-rpc-server-example")
    private HelloWorldService helloWorldService;
    
    @RequestMapping("sayHello")
    public String sayHello() {
        // 调用了远程服务
        helloWorldService.sayHello("TestController");
        return "OK";
    }
}
```
代码中examples模块有完整用例可供参考

