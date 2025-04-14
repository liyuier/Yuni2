package com.yuier.yuni.core.randosoru.plugin;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.detect.message.pattern.PatternDetector;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.event.OneBotEventPosition;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.notice.NoticeEvent;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.domain.plugin.YuniNegativePlugin;
import com.yuier.yuni.common.domain.plugin.YuniNoticePlugin;
import com.yuier.yuni.common.domain.plugin.YuniPlugin;
import com.yuier.yuni.common.enums.OneBotEventEnum;
import com.yuier.yuni.common.enums.PermissionLevel;
import com.yuier.yuni.common.enums.SubscribeCondition;
import com.yuier.yuni.common.interfaces.detector.EventDetector;
import com.yuier.yuni.common.interfaces.detector.message.MessageDetector;
import com.yuier.yuni.common.interfaces.detector.notice.NoticeDetector;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.interfaces.plugin.NegativePluginBean;
import com.yuier.yuni.common.interfaces.plugin.NoticePluginBean;
import com.yuier.yuni.common.interfaces.plugin.PluginBean;
import com.yuier.yuni.common.utils.BeanCopyUtils;
import com.yuier.yuni.core.randosoru.bot.BotManager;
import com.yuier.yuni.core.randosoru.perm.PermissionManager;
import com.yuier.yuni.core.randosoru.subscribe.SubscribeManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.yuier.yuni.common.constants.SystemConstants.SUBSCRIBE_PLUGIN;
import static com.yuier.yuni.common.constants.SystemConstants.UNSUBSCRIBE_PLUGIN;

/**
 * @Title: PluginManager
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.plugin
 * @Date 2024/11/9 0:34
 * @description: 插件容器
 */

@Slf4j
@Data
@Component
public class PluginManager {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BotManager botManager;
    @Autowired
    PermissionManager permissionManager;
    @Autowired
    SubscribeManager subscribeManager;

    // 消息事件触发的插件集合
    private HashMap<Integer, YuniMessagePlugin> orderMessagePluginMap;
    private HashMap<Integer, YuniMessagePlugin> patternMessagePluginMap;

    // 通知事件触发的插件合集
    private HashMap<Integer, YuniNoticePlugin> noticePluginHashMap;

    // 使用过的插件名称，用于辅助构建插件
    private HashMap<String, String> pluginNamesUsed;

    public Integer totalPluginNumber;

    public PluginManager() {
        totalPluginNumber = 0;
        orderMessagePluginMap = new HashMap<>();
        patternMessagePluginMap = new HashMap<>();
        noticePluginHashMap = new HashMap<>();
        pluginNamesUsed = new HashMap<>();
    }

    public YuniPlugin getPluginById(Integer pluginId) {
        if (pluginId <= 0 || pluginId > totalPluginNumber) {
            throw new RuntimeException("插件 ID 不在范围中！");
        }
        if (orderMessagePluginMap.containsKey(pluginId)) {
            return orderMessagePluginMap.get(pluginId);
        }
        if (patternMessagePluginMap.containsKey(pluginId)) {
            return patternMessagePluginMap.get(pluginId);
        }
        if (noticePluginHashMap.containsKey(pluginId)) {
            return noticePluginHashMap.get(pluginId);
        }
        return null;
    }

    /**
     * 匹配 OneBot 上报事件，为每个插件
     * @param event OneBot 事件
     * @param <T> 限定入参为 OneBotEvent 类型
     */
    public <T extends OneBotEvent> void matchEventForPlugin(T event) {
        // 如果事件为消息事件
        if (event instanceof MessageEvent) {
            // 检查是否能命中某个插件，并执行
            matchAndExecMessageEvent((MessageEvent<?>) event);
        } else if (event instanceof NoticeEvent) {
            // 如果事件为通知事件
            matchAndExecNoticeEvent((NoticeEvent) event);
        }
    }

    /**
     * 匹配通知事件
     * @param event 通知事件
     */
    public void matchAndExecNoticeEvent(NoticeEvent event) {
        for (YuniNoticePlugin plugin : noticePluginHashMap.values()) {
            // 检查插件是否被当前 BOT 实例订阅
            if (!pluginIsSubscribedAtThePosition(event, plugin)) {
                continue;
            }
            // 检查当前插件是否被消息命中
            if (NoticeMatcher.matchNoticeDetector(event, plugin)) {
                // 如果命中，直接执行插件即可
                callPlugin(plugin, event);
            }
        }
    }

    /**
     * 匹配消息事件
     * @param event 消息事件
     */
    public void matchAndExecMessageEvent(MessageEvent<?> event) {
        /*
         * 原则是先匹配指令插件。如果存在指令插件可以被触发，后续不再遍历模式插件。
         * 如果没有匹配上指令插件，再继续匹配模式插件。模式插件匹配上了也继续匹配，不中途退出。
         */
        boolean orderPluginCalled = false;
        for (YuniMessagePlugin plugin: orderMessagePluginMap.values()) {
            // 检查插件是否被当前位置订阅
            if (!pluginIsSubscribedAtThePosition(event, plugin)) {
                continue;
            }
            // 检查当前插件是否被消息命中
            if (MessageMatcher.matchOrderMessage(event, plugin)) {
                // 如果命中了，检查消息发送者是否有权调用该插件
                if (!checkPermission(event, plugin)) {
                    // 如果无权调用，发出提示
                    replyNoPermission();
                    continue;
                }
                // 如果有权调用，执行插件
                callPlugin(plugin, event);
                orderPluginCalled = true;
            }
        }
        // 如果存在指令插件被触发，后续不再遍历匹配插件
        if (orderPluginCalled) {
            return;
        }
        for (YuniMessagePlugin plugin : patternMessagePluginMap.values()) {
            // 检查插件是否被当前 BOT 实例订阅
            if (!pluginIsSubscribedAtThePosition(event, plugin)) {
                continue;
            }
            // 检查当前插件是否被消息命中
            if (MessageMatcher.matchPatternDetector(event, plugin)) {
                // 如果命中了，检查消息发送者是否有权调用该插件
                if (!checkPermission(event, plugin)) {
                    // 如果无权调用，发出提示
                    replyNoPermission();
                    continue;
                }
                // 如果有权调用，执行插件
                callPlugin(plugin, event);
            }
        }
    }

    /**
     * 执行消息插件入口函数
     * @param plugin  消息插件本体
     * @param event  消息事件
     */
    private void callPlugin(YuniMessagePlugin plugin, MessageEvent<?> event) {
        // 获取反射执行方法的必要材料
        PluginBean pluginBean = plugin.getPluginBean();
        Method runMethod = plugin.getRunMethod();
        MessageDetector detector = (MessageDetector) plugin.getDetector();
        try {
            // 反射执行插件入口函数
            runMethod.invoke(pluginBean, event, detector);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void callPlugin(YuniNoticePlugin plugin, NoticeEvent event) {
        // 获取反射执行方法的必要材料
        PluginBean pluginBean = plugin.getPluginBean();
        Method runMethod = plugin.getRunMethod();
        NoticeDetector detector = (NoticeDetector) plugin.getDetector();
        try {
            // 反射执行插件入口函数
            runMethod.invoke(pluginBean, event, detector);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 如果插件触发者无权调用插件，执行这一步
     */
    private void replyNoPermission() {
        // TODO
    }

    /**
     * 检查消息发送者是否有权调用插件
     * @param event  消息事件
     * @param plugin  插件
     * @return  消息发送者是否有权调用插件
     */
    private Boolean checkPermission(MessageEvent<?> event, YuniMessagePlugin plugin) {
        PermissionLevel userPermLevel = permissionManager.queryUserPermission(event);
        PermissionLevel pluginPermLevel = plugin.getPluginBean().getClass()
                                                .getAnnotation(Plugin.class).permission();
        return userPermLevel.compareTo(pluginPermLevel) >= 0;
    }

    ////////////////////////////////////
    // 下方为初始化部分代码
    ////////////////////////////////////

    // 初始化
    public void initialize() {
        buildYuniPlugins();
        log.info("插件管理器初始化完毕。");
    }

    /**
     * 初始化插件
     * // TODO 应当注意入参校验，但是我燃尽了，以后再写吧
     */
    private void buildYuniPlugins() {
        // 扫描所有加了 @PluginBean 注解的插件
        Map<String, Object> pluginBeans = applicationContext.getBeansWithAnnotation(Plugin.class);
        for (Object bean: pluginBeans.values()) {
            if (bean instanceof PluginBean) {
                buildYuniPluginFromPluginBean((PluginBean) bean);
            }
        }
    }

    /**
     * 由插件 Bean 构建出插件
     * @param targetPluginBean 插件 Bean 本体
     * @param <T> 泛型，限定入参实现 PluginBean 接口
     */
    private <T extends PluginBean> void buildYuniPluginFromPluginBean(T targetPluginBean) {
        // 获取插件的 @Plugin 注解
        Plugin pluginAnno = targetPluginBean.getClass().getAnnotation(Plugin.class);
        if (pluginAnno == null) {
            return;
        }
        // 初始化 yuniPlugin
        YuniPlugin yuniPlugin = new YuniPlugin();

        // 设置插件 name
        String pluginName = "";
        if (pluginAnno.name().isEmpty()) {
            // 以 Bean 的类名作为插件 name
            pluginName = targetPluginBean.getClass().getSimpleName();
        } else {
            pluginName = pluginAnno.name();
        }
        // 判断插件名称是否重复
        if (pluginNamesUsed.containsKey(pluginName)) {
            throw new RuntimeException("插件 " + targetPluginBean.getClass().getName() +
                    " 的名称 " + pluginName + "已经存在!");
        }
        pluginNamesUsed.put(pluginName, pluginName);
        yuniPlugin.setName(pluginName);

        // 设置插件的 Bean 本体
        yuniPlugin.setPluginBean(targetPluginBean);

        // 获取插件帮助信息
        String helpInfo = invokeBeanNoArgMethods(targetPluginBean, "helpInfo");
        yuniPlugin.setHelpInfo(helpInfo);

        // 获取插件默认订阅策略
        yuniPlugin.setSubmitCondition(pluginAnno.subscribe());

        // 根据插件默认订阅策略设置插件初始状态
        yuniPlugin.setIsSubscribed(yuniPlugin.getSubmitCondition().equals(SubscribeCondition.YES));

        // 插件是否为内置插件；内置插件无法删除
        yuniPlugin.setInner(pluginAnno.inner());

        // 如果插件为被动插件，继续构建
        if (targetPluginBean instanceof NegativePluginBean<?, ?>) {
            buildFurtherForNegativePlugin(yuniPlugin, (NegativePluginBean<?, ?>) targetPluginBean);
        }
    }

    /**
     * 从 YuniPlugin 实例构建出被动插件
     * @param yuniPlugin YuniPlugin 实例
     * @param targetPluginBean 插件 Bean 本体
     * @param <T> 泛型，限定入参实现 NegativePluginBean 接口
     */
    private <T extends NegativePluginBean<?, ?>> void buildFurtherForNegativePlugin(YuniPlugin yuniPlugin, T targetPluginBean) {
        YuniNegativePlugin yuniNegativePlugin = BeanCopyUtils.copyBean(yuniPlugin, YuniNegativePlugin.class);
        try {
            // 获取被动插件的入口方法
            yuniNegativePlugin.setRunMethod(targetPluginBean.getClass().getMethod("run", OneBotEvent.class, EventDetector.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 获取被动插件的事件探测器
        yuniNegativePlugin.setDetector(invokeBeanNoArgMethods(targetPluginBean, "detector"));

        // 如果被动插件由消息事件触发，继续构建
        if (targetPluginBean instanceof MessagePluginBean) {
            buildFurtherForMessagePlugin(yuniNegativePlugin, (MessagePluginBean<?>) targetPluginBean);
        } else if (targetPluginBean instanceof NoticePluginBean<?>) {
            // 如果被动插件由通知事件触发，继续构建
            buildFurtherForNoticePlugin(yuniNegativePlugin, (NoticePluginBean<?>) targetPluginBean);
        }
    }

    /**
     * 从 YuniNegativePlugin 实例构建出 MessagePluginBean 消息插件
     * @param yuniNegativePlugin YuniNegativePlugin 实例
     * @param targetPluginBean 插件 Bean 本体
     * @param <T> 泛型，限定入参实现 NegativePluginBean 接口
     */
    private <T extends MessagePluginBean<?>> void buildFurtherForMessagePlugin(YuniNegativePlugin yuniNegativePlugin, T targetPluginBean) {
        YuniMessagePlugin yuniMessagePlugin = BeanCopyUtils.copyBean(yuniNegativePlugin, YuniMessagePlugin.class);

        // 获取插件的 @Plugin 注解
        Plugin pluginAnno = targetPluginBean.getClass().getAnnotation(Plugin.class);
        if (pluginAnno == null) {
            return;
        }
        // 设置触发该消息插件的权限等级
        yuniMessagePlugin.setPermission(pluginAnno.permission());
        // 设置该消息插件监听的消息类型
        yuniMessagePlugin.setListener(invokeBeanNoArgMethods(targetPluginBean, "listenAt"));

        // 构建结束（たぶん），将插件加入 messagePluginMap 中
        putMessagePluginsIntoMap(yuniMessagePlugin);
    }

    /**
     * 从 YuniNegativePlugin 实例构建出 YuniNegativePlugin 消息插件
     * @param yuniNegativePlugin  YuniNegativePlugin 实例
     * @param targetPluginBean  插件 Bean 本体
     */
    private void buildFurtherForNoticePlugin(YuniNegativePlugin yuniNegativePlugin, NoticePluginBean<?> targetPluginBean) {
        YuniNoticePlugin yuniNoticePlugin = BeanCopyUtils.copyBean(yuniNegativePlugin, YuniNoticePlugin.class);

        Plugin pluginAnno = targetPluginBean.getClass().getAnnotation(Plugin.class);
        if (pluginAnno == null) {
            return;
        }
        // 将插件加入 noticePluginHashMap 中
        totalPluginNumber ++;
        yuniNoticePlugin.setId(totalPluginNumber);
        noticePluginHashMap.put(totalPluginNumber, yuniNoticePlugin);
    }


    /**
     * 将消息插件按探测器类型放入不同插件 map 中
     * @param yuniMessagePlugin  消息插件
     */
    private void putMessagePluginsIntoMap(YuniMessagePlugin yuniMessagePlugin) {
        EventDetector<?> detector = yuniMessagePlugin.getDetector();
        if (detector instanceof OrderDetector) {
            totalPluginNumber ++;
            yuniMessagePlugin.setId(totalPluginNumber);
            orderMessagePluginMap.put(totalPluginNumber, yuniMessagePlugin);
        } else if (detector instanceof PatternDetector) {
            totalPluginNumber ++;
            yuniMessagePlugin.setId(totalPluginNumber);
            patternMessagePluginMap.put(totalPluginNumber, yuniMessagePlugin);
        }
    }

    /**
     * 小工具，用于获取目标插件 Bean 的一些无参方法的执行结果
     * @param targetBean 目标插件 Bean
     * @param methodName 方法名
     * @return 方法执行结果
     * @param <T> 用于限制返回值
     * @param <S> 用于限制 Bean 实现 PluginBean 接口
     */
    private <T, S extends PluginBean> T invokeBeanNoArgMethods(S targetBean, String methodName) {
        Method targetMethod = null;
        try {
            targetMethod = targetBean.getClass().getMethod(methodName);
            return (T) targetMethod.invoke(targetBean);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断当前位置下的事件是否订阅了插件
     * @param event 事件
     * @param plugin  插件
     * @return  是否订阅
     */
    public Boolean pluginIsSubscribedAtThePosition(OneBotEvent event, YuniPlugin plugin) {
        return subscribeManager.querySubscCondition(event, plugin).equals(SubscribeCondition.YES);
    }

    /**
     * 在当前位置下订阅插件
     * @param position 当前位置
     * @param pluginId 插件 ID
     */
    public void subscribePlugin(OneBotEventPosition position, Integer pluginId) {
        // 设置当前位置下插件 id 特殊订阅情况为 true
        String positionType = position.getPositionStr();
        Long positionId = position.getPositionId();
        String pluginName = getPluginNameById(pluginId);
        OneBotEventEnum pluginType = getPluginTypeById(pluginId);

        if (pluginName != null && pluginType != null) {
            subscribeManager.setSubscExcepCondition(positionType, positionId, pluginName, SUBSCRIBE_PLUGIN);
        }
    }

    /**
     * 根据插件 ID 获取插件名称
     * @param pluginId 插件 ID
     * @return 插件名称
     */
    public String getPluginNameById(Integer pluginId) {
        if (pluginIdLegal(pluginId)) {
            // 好丑陋的写法
            if (orderMessagePluginMap.containsKey(pluginId)) {
                return orderMessagePluginMap.get(pluginId).getName();
            }
            if (patternMessagePluginMap.containsKey(pluginId)) {
                return patternMessagePluginMap.get(pluginId).getName();
            }
            if (noticePluginHashMap.containsKey(pluginId)) {
                return noticePluginHashMap.get(pluginId).getName();
            }
        }
        return null;
    }

    /**
     * 根据插件 ID 获取插件类型
     * @param pluginId 插件 ID
     * @return 插件类型，粒度拉齐 OneBot 事件类型
     */
    public OneBotEventEnum getPluginTypeById(Integer pluginId) {
        if (orderMessagePluginMap.containsKey(pluginId) ||
                patternMessagePluginMap.containsKey(pluginId)) {
            return OneBotEventEnum.MESSAGE;
        }
        if (noticePluginHashMap.containsKey(pluginId)) {
            return OneBotEventEnum.NOTICE;
        }
        return null;
    }

    /**
     * 检查传入的插件 ID 是否合法，即在 1 与最大插件数之间
     * @param pluginId 插件 ID
     * @return ID 是否合法
     */
    public Boolean pluginIdLegal(Integer pluginId) {
        return pluginId >= 1 && pluginId <= totalPluginNumber;
    }

    public void unsubscribePlugin(OneBotEventPosition position, Integer pluginId) {
        // 设置当前位置下插件 id 特殊订阅情况为 true
        String positionType = position.getPositionStr();
        Long positionId = position.getPositionId();
        String pluginName = getPluginNameById(pluginId);
        if (pluginName != null) {
            subscribeManager.setSubscExcepCondition(positionType, positionId, pluginName, UNSUBSCRIBE_PLUGIN);
        }

    }
}
