package com.yuier.yuni.core.util;

import com.yuier.yuni.common.utils.CallYuniServiceUtil;
import com.yuier.yuni.core.domain.pojo.request.BALogoPojo;
import com.yuier.yuni.core.domain.pojo.request.PluginDetailPojo;
import com.yuier.yuni.core.domain.pojo.request.PluginsInfoPicPojo;
import com.yuier.yuni.core.domain.pojo.response.PythonUtilImageRes;
import com.yuier.yuni.core.domain.pojo.response.SayHelloToPythonPojo;
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
        return pyCaller.getOneBotForEntity("apis/hello", SayHelloToPythonPojo.class);
    }

    public PythonUtilImageRes getPluginsInfo(PluginsInfoPicPojo requestPojo) {
        CallYuniServiceUtil pyCaller = getPythonServiceCaller();
        return pyCaller.postOneBotForEntity(
                "plugin/list",
                requestPojo,
                PluginsInfoPicPojo.class,
                PythonUtilImageRes.class
        );
    }

    public PythonUtilImageRes getPluginDetailInfo(PluginDetailPojo pluginDetailPojo) {
        CallYuniServiceUtil pyCaller = getPythonServiceCaller();
        return pyCaller.postOneBotForEntity(
                "plugin/detail",
                pluginDetailPojo,
                PluginDetailPojo.class,
                PythonUtilImageRes.class
        );
    }

    public PythonUtilImageRes getBaLogoPojo(BALogoPojo baLogoPojo) {
        CallYuniServiceUtil pyCaller = getPythonServiceCaller();
        return pyCaller.postOneBotForEntity(
                "logo/ba",
                baLogoPojo,
                BALogoPojo.class,
                PythonUtilImageRes.class
        );
    }
}
