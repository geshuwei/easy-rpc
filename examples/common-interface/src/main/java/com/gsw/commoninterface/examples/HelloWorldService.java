package com.gsw.commoninterface.examples;


import com.gsw.easyrpc.server.EasyRpcService;

public interface HelloWorldService extends EasyRpcService {

    void sayHello(String greetFrom);

    String getMyName(String greetFrom);

}
