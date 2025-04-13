package com.yuier.yuni.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.core.util.EventHandlerPatcher;
import com.yuier.yuni.common.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: OneBotEventController
 * @Author yuier
 * @Package com.yuier.yuni.core.Controller
 * @Date 2024/11/10 17:59
 * @description: OneBot 上报消息事件的 controller
 */

@RestController
@RequestMapping("/")
public class OneBotEventController {

    @Autowired
    EventHandlerPatcher patcher;

    @Autowired
    ObjectMapper objectMapper;

//    @PostMapping
//    public <T extends OneBotEvent> ResponseResult<?> oneBotEventEntrance(@RequestBody T event) {
//        patcher.eventPreHandle(event);
//        patcher.patchEvent(event);
//        return ResponseResult.okResult();
//    }

    @PostMapping
    public ResponseResult<?> oneBotEventEntrance(@RequestBody String jsonBody) {
        OneBotEvent event = null;
        try {
            event = objectMapper.readValue(jsonBody, OneBotEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        patcher.eventPreHandle(event);
        patcher.patchEvent(event);
        return ResponseResult.okResult();
    }
}
