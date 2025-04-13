package com.yuier.yuni.core.randosoru.subscribe;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.domain.plugin.YuniPlugin;
import com.yuier.yuni.common.enums.SubscribeCondition;
import com.yuier.yuni.common.utils.RedisCache;
import com.yuier.yuni.core.domain.entity.PluginSubscExceptEntity;
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
    public SubscribeCondition querySubscCondition(MessageEvent<?> event, YuniPlugin plugin) {
        Integer subscExcepCondition = querySubscExcepCondition(event, plugin);
        if (subscExcepCondition.equals(UNKNOWN_SUBSC_CONDITION)) {
            // 没有查出特殊订阅情况，构造默认订阅情况
            return plugin.getPluginBean()
                        .getClass().getAnnotation(Plugin.class)
                        .subscribe();
        }
        return getSubscByFlag(subscExcepCondition);
    }

    public SubscribeCondition querySubscCondition(NoticeEvent event, YuniPlugin plugin) {
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
    private Integer querySubscExcepCondition(MessageEvent<?> event, YuniPlugin plugin) {
        String position = event.getPosition().getMessageType().toString();
        Long posId = event.getPosition().getPositionId();
        String pluginName = plugin.getName();
        return querySubscExcepCondition(position, posId, pluginName);
    }

    private Integer querySubscExcepCondition(NoticeEvent event, YuniPlugin plugin) {
        Long posId = event.getPosition().getPositionId();
        String pluginName = plugin.getName();
        return querySubscExcepCondition(posId, pluginName);
    }

    /**
     * 查找订阅特殊情况
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginName 插件 name
     * @return 订阅特殊情况数字
     */
    private Integer querySubscExcepCondition(String position, Long posId, String pluginName) {
        // 到 redis 中查询特殊订阅情况
        Map<String, Integer> subscExceptMap = redisCache.getCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP);
        if (subscExceptMap.isEmpty()) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        String redisSubscKey = buildRedisSubscKey(position, posId, pluginName);
        if (!subscExceptMap.containsKey(redisSubscKey)) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        return subscExceptMap.get(redisSubscKey);
    }

    private Integer querySubscExcepCondition(Long posId, String pluginName) {
        // 到 redis 中查询特殊订阅情况
        Map<String, Integer> subscExceptMap = redisCache.getCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP);
        if (subscExceptMap.isEmpty()) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        String redisSubscKey = buildRedisSubscKey(posId, pluginName);
        if (!subscExceptMap.containsKey(redisSubscKey)) {
            return UNKNOWN_SUBSC_CONDITION;
        }
        return subscExceptMap.get(redisSubscKey);
    }

    /**
     * 设置特殊订阅情况表
     * @param positionType 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginName 插件 ID
     * @param subscFlag 特殊订阅情况
     */
    public void setSubscExcepCondition(String positionType, Long posId, String pluginName, Integer subscFlag) {
        // 刷新 redis 中的特殊情况表
        refreshRedisSubscExcepMap(positionType, posId, pluginName, subscFlag);
        // 刷新数据库中的特殊情况表
        refreshDbSubsc(positionType, posId, pluginName, subscFlag);
    }

    public void setSubscExcepCondition(Long posId, String pluginName, Integer subscFlag) {
        // 刷新 redis 中的特殊情况表
        refreshRedisSubscExcepMap(posId, pluginName, subscFlag);
        // 刷新数据库中的特殊情况表
        refreshDbSubsc(posId, pluginName, subscFlag);

    }

    /**
     * 刷新数据库中的特殊订阅情况表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginName 插件 ID
     * @param subscFlag 特殊订阅情况
     */
    public void refreshDbSubsc(String position, Long posId, String pluginName, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        pluginSubscExceptService.refreshDbSubsc(position, posId, pluginName, subscFlag);
    }

    public void refreshDbSubsc(Long posId, String pluginName, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        pluginSubscExceptService.refreshDbSubsc(posId, pluginName, subscFlag);
    }

    /**
     * 刷新 redis 中的特殊订阅情况表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginName 插件 name
     * @param subscFlag 特殊订阅情况
     */
    private void refreshRedisSubscExcepMap(String position, Long posId, String pluginName, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        String redisSubscKey = buildRedisSubscKey(position, posId, pluginName);
        Map<String, Integer> subscExceptMap;
        subscExceptMap = redisCache.getCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP);
        if (subscExceptMap == null) {
            subscExceptMap = new HashMap<>();
        }
        subscExceptMap.put(redisSubscKey, subscFlag);
        redisCache.setCacheMap(SUBSCRIBE_PLUGIN_EXCEP_MAP, subscExceptMap);
    }

    private void refreshRedisSubscExcepMap(Long posId, String pluginName, Integer subscFlag) {
        if (!subscFlag.equals(SUBSCRIBE_OFF) && !subscFlag.equals(SUBSCRIBE_ON)) {
            throw new RuntimeException("错误的订阅类型：" + subscFlag);
        }
        String redisSubscKey = buildRedisSubscKey(posId, pluginName);
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
     *         position:posId:pluginName
     */
    private String buildRedisSubscKey(PluginSubscExceptEntity entity) {
        return buildRedisSubscKey(
                entity.getPosition(),
                entity.getPosId(),
                entity.getPluginName()
        );
    }

    /**
     * 构建 redis 中存储订阅特殊情况的 Map 结构的表
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param pluginName 插件 ID
     * @return 构建出的键
     */
    private String buildRedisSubscKey(String position, Long posId, String pluginName) {
        return position + ":" +
                posId + ":" +
                pluginName;
    }

    private String buildRedisSubscKey(Long posId, String pluginName) {
        return posId + ":" +
                pluginName;
    }
}
