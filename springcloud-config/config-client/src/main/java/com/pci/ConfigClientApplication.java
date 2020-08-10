package com.pci;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class,args);
    }

    @Value("${spring.redis.host}")
    String host;

    @Value("${spring.redis.port}")
    String port;

    /*@Value("${democonfigclient.message}")
    String msg;*/

    @RequestMapping(value="/findConfig")
    public String findConfig(){
        return host+":"+port;
    }

    /*@GetMapping("/findMsg")
    public String findMsg(){
        return msg;
    }*/

}
