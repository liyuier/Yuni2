package com.yuier.yuni.core.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @Title: RegisterObjectMapperUtil
 * @Author yuier
 * @Package com.yuier.yuni.core.util
 * @Date 2025/4/13 17:49
 * @description: 初始化时在 ObjectMapper 中注册子类
 */

@Slf4j
@Component
public class RegisterObjectMapperUtil {

    @Autowired
    ObjectMapper mapper;

    public void registerSubtypesRecursively() {
        // TODO 待优化
        String packagePath = "com.yuier.yuni.common";
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packagePath));
        Set<Class<?>> processed = new HashSet<>();

        // 获取所有被 @JsonTypeInfo 注解的基类（包括中间层）
        Set<Class<?>> baseClasses = reflections.getTypesAnnotatedWith(JsonTypeInfo.class);
        for (Class<?> baseClass : baseClasses) {
            registerSubtypesOf(mapper, baseClass, reflections, processed);
        }
    }

    private void registerSubtypesOf(ObjectMapper mapper, Class<?> baseClass, Reflections reflections, Set<Class<?>> processed) {
        if (processed.contains(baseClass)) return;
        processed.add(baseClass);

        // 获取当前基类的所有直接子类
        Set<Class<?>> subTypes = (Set<Class<?>>) reflections.getSubTypesOf(baseClass);
        for (Class<?> subType : subTypes) {
            // 跳过接口和抽象类
            if(subType.isInterface() || Modifier.isAbstract(subType.getModifiers())){
                continue;
            }
            // 注册子类到 ObjectMapper
            registerSubtype(mapper, subType);

            // 如果子类本身也是基类（有 @JsonTypeInfo），递归处理
            if (subType.isAnnotationPresent(JsonTypeInfo.class)) {
                registerSubtypesOf(mapper, subType, reflections, processed);
            }
        }
    }

    private void registerSubtype(ObjectMapper mapper, Class<?> subType) {
        JsonTypeDefine typeName = subType.getAnnotation(JsonTypeDefine.class);
        String typeId = (typeName != null) ? typeName.value() : subType.getSimpleName().toLowerCase();
        mapper.registerSubtypes(new NamedType(subType, typeId));
        log.info("Registered subtype: " + subType.getName() + " as " + typeId);
    }
}
