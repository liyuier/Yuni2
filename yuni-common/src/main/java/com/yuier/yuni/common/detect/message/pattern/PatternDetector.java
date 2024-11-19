package com.yuier.yuni.common.detect.message.pattern;

import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.interfaces.detector.MessageDetector;
import com.yuier.yuni.common.interfaces.detector.pattern.PatternMatcher;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Title: PatternDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.detect.message.pattern
 * @Date 2024/11/19 23:40
 * @description: 模式探测器
 */

@Data
@AllArgsConstructor
public class PatternDetector implements MessageDetector {

    private PatternMatcher matcher;

    @Override
    public Boolean hit(MessageEvent<?> event) {
        return matcher.match(event.getMessageChain());
    }

    @Override
    public Boolean defineValid() {
        return matcher != null;
    }
}
