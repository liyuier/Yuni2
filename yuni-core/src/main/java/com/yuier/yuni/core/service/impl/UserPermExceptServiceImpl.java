package com.yuier.yuni.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.yuni.core.mapper.UserPermExceptMapper;
import com.yuier.yuni.core.service.UserPermExceptService;
import com.yuier.yuni.core.domain.entity.UserPermExceptEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * (UserPermExcept)表服务实现类
 *
 * @author liyuier
 * @since 2024-11-28 00:18:52
 */

@Slf4j
@Service
public class UserPermExceptServiceImpl extends ServiceImpl<UserPermExceptMapper, UserPermExceptEntity> implements UserPermExceptService {

    @Override
    public List<UserPermExceptEntity> getPermExceptList() {
        List<UserPermExceptEntity> userPermExceptEntityList = list();
        return Objects.requireNonNullElseGet(userPermExceptEntityList, ArrayList::new);
    }

    /**
     * 查找数据库中当前定位下是否存在记录
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @return 数据库中的记录
     */
    @Override
    public List<UserPermExceptEntity> listUserPerms(Long userId, String position, Long posId, Long botId) {
        LambdaQueryWrapper<UserPermExceptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermExceptEntity::getUserId, userId);
        wrapper.eq(UserPermExceptEntity::getPosition, position);
        wrapper.eq(UserPermExceptEntity::getPosId, posId);
        wrapper.eq(UserPermExceptEntity::getBotId, botId);
        return list(wrapper);
    }

    /**
     * 增加记录
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @param permLevel 权限等级
     */
    @Override
    public void addUserPerm(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        UserPermExceptEntity userPermExceptEntity = new UserPermExceptEntity(userId, position, posId, botId, permLevel);
        save(userPermExceptEntity);
    }

    /**
     * 刷新记录
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @param permLevel 权限等级
     */
    @Override
    public void updateUserPerm(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        List<UserPermExceptEntity> userPermExceptEntityList = listUserPerms(userId, position, posId, botId);
        if (userPermExceptEntityList == null || userPermExceptEntityList.isEmpty()) {
            log.error("未查询到对应记录！");
            return;
        }
        UserPermExceptEntity userPermExceptEntity = userPermExceptEntityList.get(FIRST_INDEX);
        userPermExceptEntity.setPermLevel(permLevel);
        updateById(userPermExceptEntity);
    }
}

