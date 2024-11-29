package com.yuier.yuni.core.randosoru.subscribe;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.enums.SubscribeCondition;
import com.yuier.yuni.common.utils.RedisCache;
import com.yuier.yuni.core.domain.entity.PluginSubscExceptEntity;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import com.yuier.yuni.core.service.PluginSubscExceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: SubscribeManager
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.subscribe
 * @Date 2024/11/29 19:11
 * @description: 订阅管理器
 */

@Component
public class SubscribeManager {

    @Autowired
    PluginManager pluginManager;
    @Autowired
    PluginSubscExceptService pluginSubscExceptService;
    @Autowired
    RedisCache redisCache;

    private static final String SUBSCRIBE_PLUGIN_EXCEP_MAP = "subsc:plugin:excep:map";
    // 订阅插件
    private static final Integer SUBSCRIBE_ON = 1;
    // 取消订阅插件
    private static final Integer SUBSCRIBE_OFF = 0;
    private static final Integer UNKNOWN_SUBSC_CONDITION = -1;

    private static final HashMap<Integer, SubscribeCondition> subscMap = new HashMap<>();

    /**
     * 订阅系统初始化
     */
    public void initialize() {
        // 从数据库中读取订阅特殊情况表，加载到 redis 中
        List<PluginSubscExceptEntity> subscExceptEntityList = pluginSubscExceptService.getSubscExceptList();
        Map<String, Integer> subscExcepMap = subscExceptEntityList.stream()
                .collect(Collectors.toMap(this::buildRedisSubscKey, PluginSubscExceptEntity::getSubscFlag));
        redisCache.setCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP, subscExcepMap);
        // 初始化 subscMap
        initializeSubscMap();
    }

    /**
     * 初始化 subscMap
     */
    private void initializeSubscMap() {
        subscMap.put(SUBSCRIBE_ON, SubscribeCondition.YES);
        subscMap.put(SUBSCRIBE_OFF, SubscribeCondition.NO);
    }

    /**
     * 查询插件订阅情况，仅适配消息事件
     * @param event 消息事件
     * @return 订阅情况
     */
    public SubscribeCondition querySubscCondition(MessageEvent<?> event, YuniMessagePlugin plugin) {
        Integer subscExcepCondition = querySubscExcepCondition(event, plugin);
        if (subscExcepCondition.equals(UNKNOWN_SUBSC_CONDITION)) {
            // 没有查出特殊订阅情况，构造默认订阅情况
            return plugin.getPluginBean()
                        .getClass().getAnnotation(Plugin.class)
                        .subscribe();
        }
        return getSubscByFlag(subscExcepCondition);
    }

    /**
     * 根据订阅数字返回订阅情况
     * @param subscFlag 订阅数字 flag
     * @return 订阅情况
     */
    private SubscribeCondition getSubscByFlag(Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        return subscMap.get(subscFlag);
    }

    /**
     * 查找订阅特殊情况
     * @param event 消息事件
     * @return 订阅情况数字
     */
    private Integer querySubscExcepCondition(MessageEvent<?> event, YuniMessagePlugin plugin) {
        String position = event.getPosition().getMessageType().toString();
        Long posId = event.getPosition().getPositionId();
        String pluginId = plugin.getId();
        return querySubscExcepCondition(position, posId, pluginId);
    }

    /**
     * 查找订阅特殊情况
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginId 插件 ID
     * @return 订阅特殊情况数字
     */
    private Integer querySubscExcepCondition(String position, Long posId, String pluginId) {
        // 到 redis 中查询特殊订阅情况
        Map<String, Integer> subscExceptMap = redisCache.getCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP);
        if (subscExceptMap.isEmpty()) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        String redisSubscKey = buildRedisSubscKey(position, posId, pluginId);
        if (!subscExceptMap.containsKey(redisSubscKey)) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        return subscExceptMap.get(redisSubscKey);
    }

    /**
     * 设置特殊订阅情况表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginId 插件 ID
     * @param subscFlag 特殊订阅情况
     */
    public void setSubscExcepCondition(String position, Long posId, String pluginId, Integer subscFlag) {
        // 刷新 redis 中的特殊情况表
        refreshRedisSubscExcepMap(position, posId, pluginId, subscFlag);
        // 刷新数据库中的特殊情况表
        refreshDbSubsc(position, posId, pluginId, subscFlag);
    }

    /**
     * 刷新数据库中的特殊订阅情况表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginId 插件 ID
     * @param subscFlag 特殊订阅情况
     */
    private void refreshDbSubsc(String position, Long posId, String pluginId, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        // 首先查找当前定位下是否已经设置了特殊订阅情况
        List<PluginSubscExceptEntity> subscExceptEntityList = pluginSubscExceptService.listSubscs(position, posId, pluginId);
        if (subscExceptEntityList == null || subscExceptEntityList.isEmpty()) {
            pluginSubscExceptService.addSubsc(position, posId, pluginId, subscFlag);
        } else {
            pluginSubscExceptService.updateSubsc(position, posId, pluginId, subscFlag);
        }
    }

    /**
     * 刷新 redis 中的特殊订阅情况表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginId 插件 ID
     * @param subscFlag 特殊订阅情况
     */
    private void refreshRedisSubscExcepMap(String position, Long posId, String pluginId, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        String redisSubscKey = buildRedisSubscKey(position, posId, pluginId);
        Map<String, Integer> subscExceptMap;
        subscExceptMap = redisCache.getCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP);
        if (subscExceptMap == null) {
            subscExceptMap = new HashMap<>();
        }
        subscExceptMap.put(redisSubscKey, subscFlag);
        redisCache.setCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP, subscExceptMap);
    }

    /**
     * 根据数据库中的记录，构建 redis 中存储订阅特殊情况的 Map 结构的表
     * @param entity 订阅特殊情况表实体类
     * @return 构建出的键
     *         position:posId:pluginId
     */
    private String buildRedisSubscKey(PluginSubscExceptEntity entity) {
        return buildRedisSubscKey(
                entity.getPosition(),
                entity.getPosId(),
                entity.getPluginId()
        );
    }

    /**
     * 构建 redis 中存储订阅特殊情况的 Map 结构的表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginId 插件 ID
     * @return 构建出的键
     */
    private String buildRedisSubscKey(String position, Long posId, String pluginId) {
        return position + ":" +
                posId + ":" +
                pluginId;
    }
}
