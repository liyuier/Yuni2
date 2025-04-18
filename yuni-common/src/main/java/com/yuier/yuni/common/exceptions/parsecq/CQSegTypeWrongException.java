package com.yuier.yuni.common.exceptions.parsecq;

/**
 * @Title: CQSegTypeWrongException
 * @Author yuier
 * @Package com.yuier.yuni.common.exceptions.parsecq
 * @Date 2025/4/18 1:02
 * @description: 无法解析 CQ 段类型错误
 */

public class CQSegTypeWrongException extends RuntimeException {

    public CQSegTypeWrongException(String cqCode, Integer errorIndex) {
        super("CQ 码非法！原字符串为: " + cqCode +
                " , 第 " + errorIndex + " 位字符处无法正确解析消息段类型！");
    }
}
