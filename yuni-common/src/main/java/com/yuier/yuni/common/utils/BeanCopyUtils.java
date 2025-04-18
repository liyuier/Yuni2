package com.yuier.yuni.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: YuniPlugin
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.plugin
 * @Date 2024/11/11 23:48
 * @description: Bean 拷贝工具类
 */

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    // 单个实体类的拷贝
    // 传入源对象与目标类的 Class 属性
    // 使用泛型，根据传入参数类型确定 Vo，返回类型与传入参数类型相同
    public static <Vo> Vo copyBean(Object source, Class<Vo> clazz) {
        // 创建目标对象
        Vo result = null;
        try {
            result = clazz.getConstructor().newInstance();
            // 实现属性 copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <O, Vo> List<Vo> copyBeanList(List<O> list, Class<Vo> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))  // map() 将集合中一种对象映射为另一种对象
                .collect(Collectors.toList());
    }
}
