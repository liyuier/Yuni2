package com.yuier.yuni.core.Controller;

import com.yuier.yuni.common.domain.event.message.GroupMessageEvent;
import com.yuier.yuni.common.utils.ResponseResult;
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

    @PostMapping
    public ResponseResult oneBotEventEntrance(@RequestBody GroupMessageEvent event) {
        return ResponseResult.okResult();
    }
}
