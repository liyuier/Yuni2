package com.yuier.yuni.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuier.yuni.core.domain.entity.UserPermExceptEntity;

import java.util.List;

/**
 * (UserPermExcept)表服务接口
 *
 * @author liyuier
 * @since 2024-11-28 00:18:51
 */
public interface UserPermExceptService extends IService<UserPermExceptEntity> {

    List<UserPermExceptEntity> getPermExceptList();
}

