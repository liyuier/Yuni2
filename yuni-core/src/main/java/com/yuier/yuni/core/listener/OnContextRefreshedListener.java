package com.yuier.yuni.core.listener;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.core.randosoru.Randosoru;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Modifier;
import java.util.Set;

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
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        // 初始化 Bot 框架
        randosoru.initialize();

        // 使 Jackson 反序列化自动适配子类
        improveJacksonDeserialize();
    }

    private void improveJacksonDeserialize() {
        String packageName = "com.yuier.yuni.common";

        Reflections reflections = new Reflections(packageName);
        // 注册子类型，使用名称建立关联
        // 使用开源库 Reflections 扫描 JsonTypeInfo 定义的基类
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(JsonTypeInfo.class);
        // 遍历基类
        for (Class<?> type : types) {
            // 使用开源库 Reflections 扫描子类
            Set<Class<?>> subClazzSet = (Set<Class<?>>) reflections.getSubTypesOf(type);
            if(CollectionUtils.isEmpty(subClazzSet)){
                continue;
            }
            // 注册子类，demo 代码，请自行修改
            for (Class<?> subClazz : subClazzSet) {
                // 跳过接口和抽象类
                if(subClazz.isInterface() || Modifier.isAbstract(subClazz.getModifiers())){
                    continue;
                }
                // 提取 JsonTypeDefine 注解
                JsonTypeDefine extendClassDefine = subClazz.getAnnotation(JsonTypeDefine.class);
                if (extendClassDefine == null) {
                    continue;
                }
                objectMapper.registerSubtypes(new NamedType(subClazz, extendClassDefine.value()));
            }
        }
        System.out.println("Done.");
    }
}
