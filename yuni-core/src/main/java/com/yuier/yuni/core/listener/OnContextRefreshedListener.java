package com.yuier.yuni.core.listener;

import com.yuier.yuni.core.randosoru.Randosoru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @Title: OnContextRefreshedListener
 * @Author yuier
 * @Package com.yuier.yuni.core.listener
 * @Date 2024/11/9 0:18
 * @description: SpringBoot 容器初始化完成后调用的 listener
 */

@Component
public class OnContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    Randosoru randosoru;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // 初始化 Bot 框架
        randosoru.initialize();
    }
}
