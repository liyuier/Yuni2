package com.yuier.yuni.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ReflectionUtils
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2025/4/14 0:00
 * @description: 反射相关工具类
 */

public class ReflectionUtils {

    /**
     * 获取目标类下添加了目标注解的方法
     * @param targetClazz  目标类
     * @param targetAnnoClazz  目标注解
     * @return  方法集合
     */
    public static List<Method> getMethodWithAnnotation(Class<?> targetClazz, Class<? extends Annotation> targetAnnoClazz) {
        // 获取类中所有方法
        Method[] declaredMethods = targetClazz.getDeclaredMethods();

        ArrayList<Method> results = new ArrayList<>();
        // 遍历方法
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(targetAnnoClazz)) {
                results.add(declaredMethod);
            }
        }
        return results;
    }
}
