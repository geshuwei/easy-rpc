package com.gsw.easyrpc.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.gsw.easyrpc.server")
public class EasyRpcServerAutoConfiguration {

    @Bean
    public static EasyRpcServerAutowireProcessor easyRpcServerAutowireProcessor() {
        return new EasyRpcServerAutowireProcessor();
    }
}
