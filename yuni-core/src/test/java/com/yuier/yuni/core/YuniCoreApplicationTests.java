package com.yuier.yuni.core;

import com.yuier.yuni.common.detect.message.order.RequiredArg;
import com.yuier.yuni.common.domain.onebotapi.data.GetLoginInfoResData;
import com.yuier.yuni.common.utils.BotAction;
import com.yuier.yuni.common.utils.CallOneBotUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuniCoreApplicationTests {

    @Test
    void contextLoads() {
        RequiredArg arg1 = new RequiredArg("name1");
        System.out.println(arg1.getAcceptType().toString());
    }

    @Test
    void callOneBot() {
        GetLoginInfoResData oneBotForEntity = CallOneBotUtil.getOneBotForEntity("http://127.0.0.1:3010/get_login_info", GetLoginInfoResData.class);
        GetLoginInfoResData loginInfo = BotAction.getLoginInfo();
        System.out.println("OK");

    }

}
