package com.yuier.yuni.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.yuni.core.domain.entity.PluginSubscExceptEntity;
import com.yuier.yuni.core.mapper.PluginSubscExceptMapper;
import com.yuier.yuni.core.service.PluginSubscExceptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * (PluginSbscExcept)表服务实现类
 *
 * @author liyuier
 * @since 2024-11-28 00:18:53
 */
@Service
public class PluginSubscExceptServiceImpl extends ServiceImpl<PluginSubscExceptMapper, PluginSubscExceptEntity> implements PluginSubscExceptService {

    @Override
    public List<PluginSubscExceptEntity> getSubscExceptList() {
        List<PluginSubscExceptEntity> subscExceptEntityList = list();
        return Objects.requireNonNullElseGet(subscExceptEntityList, ArrayList::new);
    }

    @Override
    public List<PluginSubscExceptEntity> listSubscs(String position, Long posId, String pluginId) {
        LambdaQueryWrapper<PluginSubscExceptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PluginSubscExceptEntity::getPosition, position);
        wrapper.eq(PluginSubscExceptEntity::getPosId, posId);
        wrapper.eq(PluginSubscExceptEntity::getPluginId, pluginId);
        return list(wrapper);
    }

    @Override
    public void addSubsc(String position, Long posId, String pluginId, Integer subscFlag) {
        PluginSubscExceptEntity pluginSubscExceptEntity = new PluginSubscExceptEntity(position, posId, pluginId, subscFlag);
        save(pluginSubscExceptEntity);
    }

    @Override
    public void updateSubsc(String position, Long posId, String pluginId, Integer subscFlag) {
        List<PluginSubscExceptEntity> subscExceptEntityList = listSubscs(position, posId, pluginId);
        if (subscExceptEntityList == null || subscExceptEntityList.isEmpty()) {
            log.error("未查询到对应记录！");
            return;
        }
        PluginSubscExceptEntity pluginSubscExceptEntity = subscExceptEntityList.get(FIRST_INDEX);
        pluginSubscExceptEntity.setSubscFlag(subscFlag);
        updateById(pluginSubscExceptEntity);
    }
}

