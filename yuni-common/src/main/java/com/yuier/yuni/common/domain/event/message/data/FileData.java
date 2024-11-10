package com.yuier.yuni.common.domain.event.message.data;

import com.yuier.yuni.common.anno.MessageDataEntity;
import com.yuier.yuni.common.enums.MessageDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Title: FileData
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.message.data
 * @Date 2024/4/20 17:55
 * @description: 文件 data 字段
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@MessageDataEntity(dataType = MessageDataEnum.FILE)
public class FileData extends MessageData {
    /**
     * 文件路径
     */
    private String file;
    /**
     * 发送时支持自定义显示文件名
     */
    private String name;

    public FileData(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "[文件" + "<file=" + this.file + ">]";
    }
}
