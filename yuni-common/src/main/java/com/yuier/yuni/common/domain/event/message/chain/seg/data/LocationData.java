package com.yuier.yuni.common.domain.event.message.chain.seg.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: LocationData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/14 22:13
 * @description: 位置消息段 data 类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.LOCATION)
public class LocationData {
    // 纬度
    private String lat;
    // 经度
    private String lon;
    // 发送时可选，标题
    private String title;
    // 发送时可选，内容描述
    private String content;

    @Override
    public String toString() {
        return "[位置消息<纬度=" + this.lat + "><经度=" + this.lon + ">]";
    }
}
