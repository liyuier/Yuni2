package com.yuier.yuni.core.listener;

import com.yuier.yuni.core.randosoru.Randosoru;
import com.yuier.yuni.core.util.RegisterObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class OnContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    Randosoru randosoru;
    @Autowired
    RegisterObjectMapperUtil registerObjectMapperUtil;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // 使 Jackson 反序列化自动适配子类
        registerObjectMapperUtil.registerSubtypesRecursively();
        // 初始化 Bot 框架
        randosoru.initialize();
    }
}
