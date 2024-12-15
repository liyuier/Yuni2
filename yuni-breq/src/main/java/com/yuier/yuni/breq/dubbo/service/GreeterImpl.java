package com.yuier.yuni.breq.dubbo.service;

import com.yuier.yuni.api.DubboGreeterTriple.GreeterImplBase;
import com.yuier.yuni.api.GreeterReply;
import com.yuier.yuni.api.GreeterRequest;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Title: GreeterImpl
 * @Author yuier
 * @Package com.yuier.yuni.breq.dubbo.service
 * @Date 2024/12/15 23:47
 * @description: dubbo demo service
 */

@DubboService
public class GreeterImpl extends GreeterImplBase {

    @Override
    public GreeterReply greet(GreeterRequest request) {
        System.out.println("Server received greet request " + request);
        return GreeterReply.newBuilder()
                .setMessage("hello," + request.getName())
                .build();
    }
}
