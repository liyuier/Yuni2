package com.yuier.yuni.core.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.anno.OneBotEventDispatcher;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.utils.ReflectionUtils;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import com.yuier.yuni.core.util.EventHandlerDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * @Title: EventDispatchHandler
 * @Author yuier
 * @Package com.yuier.yuni.core.handler
 * @Date 2025/4/13 23:32
 * @description: 分派事件的处理器
 */

@Component
public class EventDispatchHandler {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageEventHandler messageEventHandler;
    @Autowired
    EventHandlerDispatcher eventHandlerDispatcher;

    private static final String POST_TYPE = "post_type";
    private static final String POST_MESSAGE = "message";
    private static final String POST_NOTICE = "notice";
    private static final String POST_REQUEST = "request";
    private static final String POST_META = "meta_event";

    private static final String MESSAGE_TYPE = "message_type";
    private static final String NOTICE_TYPE = "notice_type";
    private static String POST_JSON_STR = "";

    private HashMap<String, Method> eventDispatchers = new HashMap<>();

    /**
     * 分派 OneBot 事件
     * @param postJsonNode postJsonNode
     * @return 是否成功
     */
    public Boolean dispatchOneBotEvent(JsonNode postJsonNode) {
        // 初始化
        initBaseInfos();
        // 开始之前，先进行一些基本处理
        eventHandlerDispatcher.eventPreHandle(deserializeSimply(POST_JSON_STR, OneBotEvent.class));

        // 获取 OneBot 基本事件类型
        String postType = postJsonNode.get(POST_TYPE).asText();
        Method baseEventDispatcher = eventDispatchers.get(postType);
        if (baseEventDispatcher == null) {
            return false;
        }
        try {
            // 这地方需要注意出入参
            return (Boolean) baseEventDispatcher.invoke(this, postJsonNode);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分派通知事件
     * @param postJsonNode
     * @return
     */
    private Boolean dispatchNoticeEvent(JsonNode postJsonNode) {

        return true;
    }

    /**
     * 分派消息事件
     * @param postJsonNode  postJsonNode
     * @return  是否分派成功
     */
    @OneBotEventDispatcher(POST_MESSAGE)
    private Boolean dispatchMessageEvent(JsonNode postJsonNode) {
        MessageEvent<?> messageEvent = (MessageEvent<?>) deserializeSimply(POST_JSON_STR, MessageEvent.class);
        messageEventHandler.handle(messageEvent);
        return true;
    }

    /**
     * 简单解析事件的工具类
     * @param postJson  postJson
     * @param targetClazz  targetClazz
     * @return  解析出的事件类
     */
    private OneBotEvent deserializeSimply(String postJson, Class<? extends OneBotEvent> targetClazz) {
        OneBotEvent event = null;
        try {
            // 解析事件
            event = objectMapper.readValue(postJson, targetClazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return event;
    }

    /**
     * 进行一些初始化操作
     */
    private void initBaseInfos() {
        initEventDispatcherMaps();
        initPostJsonStr();
    }

    /**
     * 初始化 POST_JSON_STR
     */
    private void initPostJsonStr() {
        POST_JSON_STR = ThreadLocalUtil.getPostJson();
    }

    /**
     * 初始化 eventDispatchers
     */
    private void initEventDispatcherMaps() {
        if (eventDispatchers != null && !eventDispatchers.isEmpty()) {
            return;
        }
        eventDispatchers = new HashMap<>();
        List<Method> methodWithAnnotation = ReflectionUtils.getMethodWithAnnotation(this.getClass(), OneBotEventDispatcher.class);
        for (Method method : methodWithAnnotation) {
            method.setAccessible(true);
            // 获取注解实例及其中内容
            OneBotEventDispatcher annotation = method.getAnnotation(OneBotEventDispatcher.class);
            String eventType = annotation.value();
            // 以注解 value 为键，以方法为值
            eventDispatchers.put(eventType, method);
        }
    }


}
