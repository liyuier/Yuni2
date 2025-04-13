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

    private static final String GENERAL_POSITION_STR = "UnImportant";

    @Override
    public List<PluginSubscExceptEntity> getSubscExceptList() {
        List<PluginSubscExceptEntity> subscExceptEntityList = list();
        return Objects.requireNonNullElseGet(subscExceptEntityList, ArrayList::new);
    }

    @Override
    public List<PluginSubscExceptEntity> listSubscs(String position, Long posId, String pluginName) {
        LambdaQueryWrapper<PluginSubscExceptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PluginSubscExceptEntity::getPosition, position);
        wrapper.eq(PluginSubscExceptEntity::getPosId, posId);
        wrapper.eq(PluginSubscExceptEntity::getPluginName, pluginName);
        return list(wrapper);
    }

    @Override
    public void addSubsc(String position, Long posId, String pluginName, Integer subscFlag) {
        PluginSubscExceptEntity pluginSubscExceptEntity = new PluginSubscExceptEntity(position, posId, pluginName, subscFlag);
        save(pluginSubscExceptEntity);
    }

    @Override
    public void updateSubsc(String position, Long posId, String pluginName, Integer subscFlag) {
        List<PluginSubscExceptEntity> subscExceptEntityList = listSubscs(position, posId, pluginName);
        if (subscExceptEntityList == null || subscExceptEntityList.isEmpty()) {
            log.error("未查询到对应记录！");
            return;
        }
        PluginSubscExceptEntity pluginSubscExceptEntity = subscExceptEntityList.get(FIRST_INDEX);
        pluginSubscExceptEntity.setSubscFlag(subscFlag);
        updateById(pluginSubscExceptEntity);
    }

    private void saveOrUpdateSubsc(String position, Long posId, String pluginName, Integer subscFlag) {
        PluginSubscExceptEntity pluginSubscExceptEntity = new PluginSubscExceptEntity(position, posId, pluginName, subscFlag);
        saveOrUpdate(pluginSubscExceptEntity);
    }

    @Override
    public void refreshDbSubsc(String position, Long posId, String pluginName, Integer subscFlag) {
        synchronized (this) {
            List<PluginSubscExceptEntity> subscExceptEntityList = listSubscs(position, posId, pluginName);
            if (subscExceptEntityList == null || subscExceptEntityList.isEmpty()) {
                addSubsc(position, posId, pluginName, subscFlag);
            } else {
                updateSubsc(position, posId, pluginName, subscFlag);
            }
        }
    }

    @Override
    public void refreshDbSubsc(Long posId, String pluginName, Integer subscFlag) {
        synchronized (this) {
            List<PluginSubscExceptEntity> subscExceptEntityList = listSubscs(GENERAL_POSITION_STR, posId, pluginName);
            if (subscExceptEntityList == null || subscExceptEntityList.isEmpty()) {
                addSubsc(GENERAL_POSITION_STR, posId, pluginName, subscFlag);
            } else {
                updateSubsc(GENERAL_POSITION_STR, posId, pluginName, subscFlag);
            }
        }
    }
}

