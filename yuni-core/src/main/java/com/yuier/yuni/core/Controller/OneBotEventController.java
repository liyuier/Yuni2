package com.yuier.yuni.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuier.yuni.common.anno.OneBotPostEntrance;
import com.yuier.yuni.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/")
public class OneBotEventController {

    @Autowired
    ObjectMapper objectMapper;

    @OneBotPostEntrance
    @PostMapping
    public ResponseResult<?> oneBotEventEntrance(@RequestBody String jsonBody) {
        log.info("收到未知事件: " + jsonBody);
        return ResponseResult.okResult();
    }
}
