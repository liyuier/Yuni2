package com.yuier.yuni.common.constants;

/**
 * @Title: SystemConstants
 * @Author yuier
 * @Package com.yuier.yuni.common.constants
 * @Date 2024/4/10 23:57
 * @description: 系统常量类
 */
public class SystemConstants {

    public static final Integer FIRST_INDEX = 0;

    public static final Integer HTTP_OK = 200;
    public static final String ONEBOT_OK_STATUS = "ok";

    public static final Integer GROUP_PLUGIN_CLOSE = 0;
    public static final Integer GROUP_PLUGIN_OPEN = 1;

    public static final class REDIS_KEY {
        public static final String BILIBILI = "bilibili";
        public static final String BILI_COOKIE = "bili:cookie";
        public static final String REFRESH_TOKEN = "ac_time_value";
    }


    // OneBot 上报数据关键字段
    public static final class POST_KEY_FIELD {

        public static final String POST_TYPE = "post_type";
        public static final String MESSAGE = "message";
        public static final String MESSAGE_TYPE = "type";
        public static final String MESSAGE_DATA = "data";
    }

    // OneBot 响应数据字段
    public static final class ONEBOT_RES_FIELD {
        public static final String STATUE = "status";
        public static final String RETCODE = "retcode";
        public static final String MESSAGE = "message";
        public static final String WORDING = "wording";
        public static final String ECHO = "echo";
        public static final String DATA = "data";
    }

    // 插件类 Bean 重要方法
    public static final class PLUGIN_CRITICAL_NAME {
        // OneBot 上报事件处理器 handler 入口方法
        public static final String HANDLER_ENTRY = "handle";

        // 功能插件入口方法
        public static final String FUNC_PLUGIN_ENTRY = "run";

        // 消息链探测器入口方法
        public static final String MSG_DETECTOR_ENTRY = "detect";

        // 功能插件 detectorDefine 方法
        public static final String DETECTOR_DEFINE = "detectorDefine";

        // 功能插件 description 方法
        public static final String DESCRIPTION = "description";
    }

}
