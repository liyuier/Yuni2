package com.yuier.yuni.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (UserPermExcept)表实体类
 *
 * @author liyuier
 * @since 2024-11-28 00:48:49
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_user_perm_except")
public class UserPermExceptEntity {
    
    /**
    * name
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
    * 用户 name
    */
    private Long userId;
    
    /**
    * 位置，group 或 private
    */
    private String position;
    
    /**
    * 位置 name
    */
    private Long posId;
    
    /**
    * bot name
    */
    private Long botId;
    
    /**
    * 权限等级，0 ~ 4
    */
    private Integer permLevel;

    public UserPermExceptEntity(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        this.userId = userId;
        this.position = position;
        this.posId = posId;
        this.botId = botId;
        this.permLevel = permLevel;
    }
}
