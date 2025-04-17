package com.yuier.yuni.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (MessageRecord)表实体类
 *
 * @author liyuier
 * @since 2025-04-17 00:50:07
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_message_record")
public class MessageRecordEntity {
    
    /**
    * id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
    * 消息 ID
    */
    private Long messageId;
    
    /**
    * 收到消息的时间
    */
    private Long time;
    
    /**
    * 格式化后的时间
    */
    private Date formattedTime;
    
    /**
    * 收到消息的 BOT ID
    */
    private Long selfId;
    
    /**
    * 发送消息的用户 ID
    */
    private Long userId;
    
    /**
    * 收到消息的群 ID，仅在收到 group 消息时有效。
    */
    private Long groupId;
    
    /**
    * 消息文本，当消息链中存在文本时储存
    */
    private String text;
    
    /**
    * CQ 码形式的消息
    */
    private String rawMessage;
    
    /**
    * 消息是否由 bot 发送。0 为否，1 为是
    */
    private Integer sentByBot;
}
