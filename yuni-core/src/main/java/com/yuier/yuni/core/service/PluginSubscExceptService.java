package com.yuier.yuni.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.yuni.core.domain.entity.PluginSubscExceptEntity;

import java.util.List;

/**
 * (PluginSbscExcept)表服务接口
 *
 * @author liyuier
 * @since 2024-11-28 00:18:53
 */
public interface PluginSubscExceptService extends IService<PluginSubscExceptEntity> {

    List<PluginSubscExceptEntity> getSubscExceptList();

    List<PluginSubscExceptEntity> listSubscs(String position, Long posId, String pluginId);

    void addSubsc(String position, Long posId, String pluginId, Integer subscFlag);

    void updateSubsc(String position, Long posId, String pluginId, Integer subscFlag);
}

