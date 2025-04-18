package com.yuier.yuni.common.exceptions.parsecq;

/**
 * @Title: CQHeadWrongException
 * @Author yuier
 * @Package com.yuier.yuni.common.exceptions
 * @Date 2025/4/18 0:58
 * @description: 无法解析 CQ 头错误
 */

public class CQHeadWrongException extends RuntimeException{

    public CQHeadWrongException(String cqCode, Integer errorIndex) {
        super("CQ 码非法！原字符串为: " + cqCode +
                " ; 第 " + errorIndex + " 位字符处无法解析为 CQ 码头。");
    }
}
