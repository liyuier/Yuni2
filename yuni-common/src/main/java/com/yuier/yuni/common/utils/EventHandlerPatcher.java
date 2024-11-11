package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.anno.EventHandler;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Title: EventHandlerPatcher
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2024/11/11 22:40
 * @description: 事件分发器
 */

@Component
public class EventHandlerPatcher {

    @Autowired
    ApplicationContext applicationContext;

    public void patchEvent(OneBotEvent event) {
        Map<String, Object> handlerBeans = applicationContext.getBeansWithAnnotation(EventHandler.class);
        for (Object bean: handlerBeans.values()) {
            Class<?> beanClass = bean.getClass();
            EventHandler handlerAnno = beanClass.getAnnotation(EventHandler.class);
            Class<? extends OneBotEvent> beanAcceptEventType = handlerAnno.eventType();
            if (beanAcceptEventType.isInstance(event)) {
                Method handle = null;
                try {
                    handle = beanClass.getMethod("handle", beanAcceptEventType);
                    handle.invoke(bean, event);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
