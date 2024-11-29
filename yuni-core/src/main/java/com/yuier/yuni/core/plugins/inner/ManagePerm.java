package com.yuier.yuni.core.plugins.inner;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import org.springframework.stereotype.Component;

/**
 * @Title: ManagePerm
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins.inner
 * @Date 2024/11/29 18:50
 * @description: 管理权限
 */

@Plugin(permission = PermissionLevel.MASTER)
@Component
public class ManagePerm implements MessagePluginBean<OrderDetector> {
    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {

    }

    @Override
    public OrderDetector detector() {
        return null;
    }

    @Override
    public String helpInfo() {
        return null;
    }
}
