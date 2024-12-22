package com.yuier.yuni.common.domain.onebotapi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: YuniBaseApiRes
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.onebotapi
 * @Date 2024/11/13 0:54
 * @description: OneBot API 响应消息体
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class YuniBaseApiRes<T>{

    private Integer retcode;

    private String message;

    private String wording;

    private String echo;

    private Integer ttl;

    private T data;

    public YuniBaseApiRes<T> build(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "YuniBaseApiRes{" +
                "retcode=" + retcode +
                ", message='" + message + '\'' +
                ", wording='" + wording + '\'' +
                ", echo='" + echo + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
