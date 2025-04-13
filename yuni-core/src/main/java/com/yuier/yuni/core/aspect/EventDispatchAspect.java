package com.yuier.yuni.core.aspect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.utils.ResponseResult;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import com.yuier.yuni.core.handler.EventDispatchHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: EventDispatchAspect
 * @Author yuier
 * @Package com.yuier.yuni.core.aspect
 * @Date 2025/4/13 23:16
 * @description: 事件分派
 */

@Aspect
@Component
public class EventDispatchAspect {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EventDispatchHandler eventDispatchHandler;

    int ARG_INDEX_FIRST;

    // 切入点为打了 @OneBotPostEntrance 注解的方法
    @Around("@annotation(com.yuier.yuni.common.anno.OneBotPostEntrance)")
    public Object dispatchOneBotEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        // 解析入参
        String postJson = (String) joinPoint.getArgs()[ARG_INDEX_FIRST];
        // 存入 ThreadLocal 中
        ThreadLocalUtil.setPostJsonStr(postJson);
        // 将字符串解析为 JsonNode
        JsonNode postJsonNode = objectMapper.readTree(postJson);

        /*
        开始分派事件
         */
        if (!eventDispatchHandler.dispatchOneBotEvent(postJsonNode)) {
            return joinPoint.proceed();
        }
        return ResponseResult.okResult();
    }
}