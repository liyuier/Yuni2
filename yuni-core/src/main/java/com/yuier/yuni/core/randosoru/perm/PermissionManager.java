package com.yuier.yuni.core.randosoru.perm;

import com.yuier.yuni.common.utils.RedisCache;
import com.yuier.yuni.core.domain.entity.UserPermExceptEntity;
import com.yuier.yuni.core.service.UserPermExceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: PermissionManager
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.perm
 * @Date 2024/11/28 0:23
 * @description: 权限管理系统
 */

@Component
public class PermissionManager {

    @Autowired
    UserPermExceptService userPermExceptService;
    @Autowired
    RedisCache redisCache;

    private static final String USER_PERM_EXCEP_MAP = "user:perm:excep:map";


    /**
     * 权限管理系统初始化
     */
    public void initialize() {
        // 从数据库中读取特殊权限信息，加载到 redis 中
        List<UserPermExceptEntity> permExceptList = userPermExceptService.getPermExceptList();
        Map<String, Integer> permExceptMap = permExceptList.stream()
                .collect(Collectors.toMap(this::buildRedisPermKey, UserPermExceptEntity::getPermLevel));
        redisCache.setCacheMap(USER_PERM_EXCEP_MAP, permExceptMap);
    }

    /**
     * 根据数据库中的记录，构建 redis 中储存权限特殊情况表的 Map 结构的键
     * @param perm 权限特殊情况表实体类
     * @return 构建出的键
     *         userId:position:positionId:botId
     */
    private String buildRedisPermKey(UserPermExceptEntity perm) {
        return perm.getUserId() + ":" +
                perm.getPosition() + ":" +
                perm.getPosId() + ":" +
                perm.getBotId();

    }
}
