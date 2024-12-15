package com.yuier.yuni.core.dubbo.client;


import com.yuier.yuni.api.Greeter;
import com.yuier.yuni.api.GreeterReply;
import com.yuier.yuni.api.GreeterRequest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Title: DubboTestTask
 * @Author yuier
 * @Package com.yuier.yuni.core.dubbo.client
 * @Date 2024/12/16 0:40
 * @description: dubbo 测试
 */

@Component
public class DubboTestTask implements CommandLineRunner {

    @DubboReference
    private Greeter greeter;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DubboTestTask.....");
        GreeterReply result = greeter.greet(GreeterRequest.newBuilder().setName("name").build());
        System.out.println("Received result ======> " + result.getMessage());
    }
}
