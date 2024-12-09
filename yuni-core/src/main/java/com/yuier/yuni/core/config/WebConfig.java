package com.yuier.yuni.core.config;

import com.yuier.yuni.core.interceptors.BotInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: WebConfig
 * @Author yuier
 * @Package com.itheima.config
 * @Date 2024/10/21 2:36
 * @description: WebConfig
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    BotInfoInterceptor botInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(botInfoInterceptor);
    }
}
