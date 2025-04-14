package com.yuier.yuni.core.randosoru.perm;

import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.sender.GroupMessageSender;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.event.message.sender.PrivateMessageSender;
import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.utils.RedisCache;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import com.yuier.yuni.core.domain.entity.UserPermExceptEntity;
import com.yuier.yuni.core.service.UserPermExceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    @Value("${master}")
    private Long masterQq;

    @Autowired
    UserPermExceptService userPermExceptService;
    @Autowired
    RedisCache redisCache;

    private static final String USER_PERM_EXCEP_MAP = "user:perm:excep:map";
    private static final Integer UNKNOWN_PERM_LEVEL = -1;
    private static final HashMap<Integer, PermissionLevel> permMap = new HashMap<>();
    private static final HashMap<String, PermissionLevel> groupMemberPermMap = new HashMap<>();
    private static final Integer PERM_BANNED = 0;
    private static final Integer PERM_USER = 1;
    private static final Integer PERM_ADMIN = 2;
    private static final Integer PERM_SUPER_ADMIN = 3;
    private static final Integer PERM_MASTER = 4;
    // 普通群成员
    private static final String GROUP_MEMBER = "member";
    // 群管理员
    private static final String GROUP_ADMIN = "admin";
    // 群主
    private static final String GROUP_OWNER = "owner";


    /**
     * 权限管理系统初始化
     */
    public void initialize() {
        // 从数据库中读取特殊权限信息，加载到 redis 中
        List<UserPermExceptEntity> permExceptList = userPermExceptService.getPermExceptList();
        Map<String, Integer> permExceptMap = permExceptList.stream()
                .collect(Collectors.toMap(this::buildRedisPermKey, UserPermExceptEntity::getPermLevel));
        redisCache.setCacheMap(USER_PERM_EXCEP_MAP, permExceptMap);
        // 初始化 permMap
        initializePermMap();
    }

    /**
     * 初始化 permMap
     */
    private void initializePermMap() {
        permMap.put(PERM_BANNED, PermissionLevel.BANNED);
        permMap.put(PERM_USER, PermissionLevel.USER);
        permMap.put(PERM_ADMIN, PermissionLevel.ADMIN);
        permMap.put(PERM_SUPER_ADMIN, PermissionLevel.SUPER_ADMIN);
        permMap.put(PERM_MASTER, PermissionLevel.MASTER);

        groupMemberPermMap.put(GROUP_MEMBER, PermissionLevel.USER);
        groupMemberPermMap.put(GROUP_ADMIN, PermissionLevel.ADMIN);
        groupMemberPermMap.put(GROUP_OWNER, PermissionLevel.SUPER_ADMIN);
    }

    /**
     * 根据数字获取权限等级
     * @param levelNum 等级数字
     * @return 权限等级
     */
    private PermissionLevel getPermByLevel(Integer levelNum) {
        if (levelNum < PERM_BANNED || levelNum > PERM_MASTER) {
            throw new RuntimeException("没有对应的权限等级：" + levelNum);
        }
        return permMap.get(levelNum);
    }

    /**
     * 根据群成员角色获取默认权限等级
     * @param groupRole 成员角色
     * @return 权限等级
     */
    private PermissionLevel getPermByGroupRole(String groupRole) {
        if (!groupMemberPermMap.containsKey(groupRole)) {
            throw new RuntimeException("不存在的群成员角色：" + groupRole);
        }
        return groupMemberPermMap.get(groupRole);
    }

    /**
     * 设置用户特殊权限
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @param permLevel 特殊权限等级
     */
    public void setUserExceptionalPermission(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        // 刷新 redis 中特殊情况表
        refreshRedisPermMap(userId, position, posId, botId, permLevel);
        // 刷新数据库中的特殊情况表
        refreshDbPerm(userId, position, posId, botId, permLevel);
    }

    /**
     * 查询用户权限，暂时只适配消息事件
     * @param event 消息事件
     * @return 用户权限等级
     */
    public PermissionLevel queryUserPermission(MessageEvent<?> event) {
        Integer exceptPermLevelNum = queryUserExceptPerm(event);
        if (exceptPermLevelNum.equals(UNKNOWN_PERM_LEVEL)) {
            // 没有查出特殊权限，构造默认权限
            MessageSender sender = event.getSender();
            // 检查发送者是否为 bot 主人
            if (sender.getUserId().equals(masterQq)) {
                return PermissionLevel.MASTER;
            }
            if (sender instanceof PrivateMessageSender) {
                // 私聊用户默认返回 USER 权限
                return PermissionLevel.USER;
            } else if (sender instanceof GroupMessageSender) {
                String groupRole = ((GroupMessageSender) sender).getRole();
                return getPermByGroupRole(groupRole);
            }
        }
        return getPermByLevel(exceptPermLevelNum);
    }

    /**
     * 查找用户权限特殊情况
     * @param event 消息事件
     * @return 权限等级数字
     */
    private Integer queryUserExceptPerm(MessageEvent<?> event) {
        Long userId = event.getSender().getUserId();
        String position = event.getPosition().getPositionStr();
        Long posId = event.getPosition().getPositionId();
        Long botId = ThreadLocalUtil.getBot().getId();
        return queryUserExceptionalPermission(userId, position, posId, botId);
    }

    /**
     * 查询用户权限特殊情况
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @return 是否有记录
     */
    private Integer queryUserExceptionalPermission(Long userId, String position, Long posId, Long botId) {
        // 到 redis 中查询用户特殊权限
        Map<String, Integer> permExceptMap = redisCache.getCacheMap(USER_PERM_EXCEP_MAP);
        if (permExceptMap.isEmpty()) {
            // 如果特殊情况表不存在，返回 -1
            return UNKNOWN_PERM_LEVEL;
        }
        String redisPermKey = buildRedisPermKey(userId, position, posId, botId);
        if (!permExceptMap.containsKey(redisPermKey)) {
            // 如果特殊情况表中不存在该定位下的值，返回 -1
            return UNKNOWN_PERM_LEVEL;
        }
        return permExceptMap.get(redisPermKey);
    }

    /**
     * 刷新持久化数据库中用户特殊权限表
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @param permLevel 特殊权限等级
     */
    private void refreshDbPerm(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        if (permLevel < PERM_BANNED || permLevel > PERM_MASTER) {
            throw new RuntimeException("没有对应的权限等级：" + permLevel);
        }
        // 首先查找当前定位下是否已经设置了特殊权限
        List<UserPermExceptEntity> userPermList = userPermExceptService.listUserPerms(userId, position, posId, botId);
        if (userPermList == null || userPermList.isEmpty()) {
            userPermExceptService.addUserPerm(userId, position, posId, botId, permLevel);
        } else {
            userPermExceptService.updateUserPerm(userId, position, posId, botId, permLevel);
        }

    }

    /**
     * 刷新 redis 中用户权限特殊情况表
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @param permLevel 特殊权限等级
     */
    private void refreshRedisPermMap(Long userId, String position, Long posId, Long botId, Integer permLevel) {
        if (permLevel < PERM_BANNED || permLevel > PERM_MASTER) {
            throw new RuntimeException("没有对应的权限等级：" + permLevel);
        }
        String redisPermKey = buildRedisPermKey(userId, position, posId, botId);
        Map<String, Integer> permExceptMap;
        permExceptMap = redisCache.getCacheMap(USER_PERM_EXCEP_MAP);
        if (permExceptMap == null) {
            permExceptMap = new HashMap<>();
        }
        permExceptMap.put(redisPermKey, permLevel);
        redisCache.setCacheMap(USER_PERM_EXCEP_MAP, permExceptMap);
    }

    /**
     * 根据数据库中的记录，构建 redis 中储存权限特殊情况表的 Map 结构的键
     * @param permEntity 权限特殊情况表实体类
     * @return 构建出的键
     *         userId:position:positionId:botId
     */
    private String buildRedisPermKey(UserPermExceptEntity permEntity) {
        return buildRedisPermKey(
                permEntity.getUserId(),
                permEntity.getPosition(),
                permEntity.getPosId(),
                permEntity.getBotId()
        );

    }

    /**
     * 构建 redis 中储存权限特殊情况表的 Map 结构的键
     * @param userId 用户 ID
     * @param position 位置，group 或 private
     * @param posId 位置 ID，群号或用户号
     * @param botId bot ID
     * @return 构建出的键
     */
    private String buildRedisPermKey(Long userId, String position, Long posId, Long botId) {
        return userId + ":" +
                position + ":" +
                posId + ":" +
                botId;
    }
}
