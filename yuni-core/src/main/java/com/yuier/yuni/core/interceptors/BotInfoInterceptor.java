package com.yuier.yuni.core.interceptors;

import com.yuier.yuni.common.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Title: BotInfoInterceptor
 * @Author yuier
 * @Package com.yuier.yuni.common.interceptors
 * @Date 2024/11/16 17:57
 * @description: bot 相关拦截器
 */

@Component
public class BotInfoInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 响应完成，清除 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
