package com.yuier.yuni.common.exceptions.parsecq;

/**
 * @Title: CQSegAttributeWrongException
 * @Author yuier
 * @Package com.yuier.yuni.common.exceptions.parsecq
 * @Date 2025/4/18 1:57
 * @description: 解析 CQ 码消息段参数错误
 */

public class CQSegAttributeWrongException extends RuntimeException {

    public CQSegAttributeWrongException(String cqCode, Integer errorIndex) {
        super("CQ 码非法！原字符串为: " + cqCode +
                " ; 第 " + errorIndex + " 位字符处无法解析为消息段参数键值对。");
    }
}
