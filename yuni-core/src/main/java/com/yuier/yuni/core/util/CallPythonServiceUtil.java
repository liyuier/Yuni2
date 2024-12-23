package com.yuier.yuni.core.util;

import com.yuier.yuni.common.utils.CallYuniServiceUtil;
import com.yuier.yuni.core.domain.pojo.SayHelloToPythonPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title: CallPythonServiceUtil
 * @Author yuier
 * @Package com.yuier.yuni.core.util
 * @Date 2024/12/24 0:38
 * @description: 调用 python 服务的工具类
 */

@Component
public class CallPythonServiceUtil {

    @Value("${yuni.service.python}")
    private String pythonServiceBaseUrl;

    private CallYuniServiceUtil pythonServiceCaller;

    private CallYuniServiceUtil getPythonServiceCaller() {
        if (pythonServiceCaller == null) {
            pythonServiceCaller = new CallYuniServiceUtil(pythonServiceBaseUrl);
        }
        return pythonServiceCaller;
    }

    public SayHelloToPythonPojo sayHello() {
        CallYuniServiceUtil pyCaller = getPythonServiceCaller();
        return pyCaller.getOneBotForEntity("api/hello", SayHelloToPythonPojo.class);
    }
}
