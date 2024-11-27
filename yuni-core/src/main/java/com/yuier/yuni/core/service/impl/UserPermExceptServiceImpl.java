package com.yuier.yuni.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuier.yuni.core.mapper.UserPermExceptMapper;
import com.yuier.yuni.core.service.UserPermExceptService;
import com.yuier.yuni.core.domain.entity.UserPermExceptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (UserPermExcept)表服务实现类
 *
 * @author liyuier
 * @since 2024-11-28 00:18:52
 */
@Service
public class UserPermExceptServiceImpl extends ServiceImpl<UserPermExceptMapper, UserPermExceptEntity> implements UserPermExceptService {

    @Override
    public List<UserPermExceptEntity> getPermExceptList() {
        List<UserPermExceptEntity> userPermExceptEntityList = list();
        return Objects.requireNonNullElseGet(userPermExceptEntityList, ArrayList::new);
    }
}

