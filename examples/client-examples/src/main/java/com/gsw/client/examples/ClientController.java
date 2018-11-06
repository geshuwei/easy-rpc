package com.gsw.client.examples;

import com.gsw.commoninterface.examples.HelloWorldService;
import com.gsw.easyrpc.common.annotations.EasyRpcClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ClientController {

    private final Log logger = LogFactory.getLog(getClass());

    @EasyRpcClient(remoteServerName = "easy-rpc-server-example")
    private HelloWorldService helloWorldService;

    @RequestMapping("")
    public String index() {
        return "hello world";
    }

    @RequestMapping("sayHello")
    public String sayHello() {
        helloWorldService.sayHello("TestController");
        return "OK";
    }

    @RequestMapping("getName")
    public String getName() {
        String name = helloWorldService.getMyName("TestController");
        logger.info(name);
        return name;
    }
}
