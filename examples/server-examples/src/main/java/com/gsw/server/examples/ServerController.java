package com.gsw.server.examples;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServerController {

    @RequestMapping("")
    public String index() {
        return "hello world";
    }
}
