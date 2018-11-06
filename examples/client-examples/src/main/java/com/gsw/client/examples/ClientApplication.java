package com.gsw.client.examples;


import com.gsw.easyrpc.client.EasyRpcClientAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@Import(EasyRpcClientAutoConfiguration.class)

public class ClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class);
    }
}
