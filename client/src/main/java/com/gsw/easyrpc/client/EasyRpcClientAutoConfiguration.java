package com.gsw.easyrpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.gsw.easyrpc.client")
public class EasyRpcClientAutoConfiguration {

    @Bean
    public EasyRpcClientAutowireProcessor easyRpcClientAutowireProcessor() {
        return new EasyRpcClientAutowireProcessor();
    }
}
