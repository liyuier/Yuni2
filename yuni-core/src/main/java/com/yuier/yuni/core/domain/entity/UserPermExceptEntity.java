package com.yuier.yuni.core.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
    * id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
    * 用户 id
    */
    private Long userId;
    
    /**
    * 位置，group 或 private
    */
    private String position;
    
    /**
    * 位置 id
    */
    private Long posId;
    
    /**
    * bot id
    */
    private Long botId;
    
    /**
    * 权限等级，0 ~ 4
    */
    private Integer permLevel;
}
