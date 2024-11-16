package com.yuier.yuni.core;

import com.yuier.yuni.common.domain.bot.BotsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class YuniCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuniCoreApplication.class, args);
    }

}
