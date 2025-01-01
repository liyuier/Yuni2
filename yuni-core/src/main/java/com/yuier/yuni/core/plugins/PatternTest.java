package com.yuier.yuni.core.plugins;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.pattern.PatternDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import org.springframework.stereotype.Component;

/**
 * @Title: PatternTest
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins
 * @Date 2024/11/19 23:51
 * @description: 模式匹配测试
 */

@Plugin
@Component
public class PatternTest implements MessagePluginBean<PatternDetector> {
    @Override
    public void run(MessageEvent<?> event, PatternDetector detector) {
        System.out.println("PatternTest entered..");
    }

    @Override
    public PatternDetector detector() {
        return new PatternDetector(
                chain -> chain.startWithTextData() && chain.atBot()
        );
    }

    @Override
    public String helpInfo() {
        return "基础匹配器插件测试";
    }
}
