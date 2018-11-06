package com.gsw.server.examples;

import com.gsw.commoninterface.examples.HelloWorldService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceServer implements HelloWorldService {

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void sayHello(String greetFrom) {
        String msg = String.format("hello %s,I'm %s", greetFrom, HelloWorldServiceServer.class);
        logger.info(msg);
    }

    @Override
    public String getMyName(String greetFrom) {
        String msg = String.format("yes %s, my name is %s", greetFrom, HelloWorldServiceServer.class);
        logger.info(msg);
        return msg;
    }
}
