package com.yuier.yuni.common.exceptions.parsecq;

/**
 * @Title: CQBracketNotClose
 * @Author yuier
 * @Package com.yuier.yuni.common.exceptions.parsecq
 * @Date 2025/4/19 0:07
 * @description: CQ 码消息段括号未闭合
 */

public class CQBracketNotClose extends RuntimeException {
    public CQBracketNotClose(String cqCode, Integer errorIndex) {
        super("CQ 码非法！原字符串为: " + cqCode +
                " , 第 " + errorIndex + " 处开始的消息段括号未闭合！");
    }
}
