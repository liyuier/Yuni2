package com.yuier.yuni.common.utils;

import com.yuier.yuni.common.domain.bot.YuniBot;

import java.util.HashMap;

import static com.yuier.yuni.common.constants.SystemConstants.LOCAL_BOT;

/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	
    //存储键值对
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove(){
        THREAD_LOCAL.remove();
    }

    // 获取 bot name
    public static YuniBot getBot() {
        HashMap<String, Object> localInfo = get();
        YuniBot currentBot = (YuniBot) localInfo.get(LOCAL_BOT);
        return currentBot;
    }
}
