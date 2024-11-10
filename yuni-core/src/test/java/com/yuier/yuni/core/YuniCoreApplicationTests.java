package com.yuier.yuni.core;

import com.yuier.yuni.common.detect.message.order.RequiredArg;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuniCoreApplicationTests {

    @Test
    void contextLoads() {
        RequiredArg arg1 = new RequiredArg("name1");
        System.out.println(arg1.getAcceptType().toString());
    }

}
