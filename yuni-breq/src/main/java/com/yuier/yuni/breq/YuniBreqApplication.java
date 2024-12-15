package com.yuier.yuni.breq;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class YuniBreqApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuniBreqApplication.class, args);
    }

}
