package com.yuier.yuni.core.randosoru.plugin;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.sender.MessageSender;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.domain.plugin.YuniNegativePlugin;
import com.yuier.yuni.common.domain.plugin.YuniPlugin;
import com.yuier.yuni.common.enums.SubmitConditions;
import com.yuier.yuni.common.interfaces.detector.EventDetector;
import com.yuier.yuni.common.interfaces.plugin.MessageCalledPluginBean;
import com.yuier.yuni.common.interfaces.plugin.NegativePluginBean;
import com.yuier.yuni.common.interfaces.plugin.PluginBean;
import com.yuier.yuni.common.utils.BeanCopyUtils;
import com.yuier.yuni.common.utils.MessageMatcher;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import com.yuier.yuni.core.randosoru.bot.BotManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: PluginManager
 * @Author yuier
 * @Package com.yuier.yuni.core.randosoru.plugin
 * @Date 2024/11/9 0:34
 * @description: 插件容器
 */

@Data
@Component
public class PluginManager {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    BotManager botManager;

    // 消息事件触发的插件集合
    private HashMap<String, YuniMessagePlugin> messagePluginMap;

    public PluginManager() {
        messagePluginMap = new HashMap<>();
    }

    // 初始化
    public void initialize() {
        buildYuniPlugins();
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
        }
    }

    /**
     * 匹配消息事件
     * @param event 消息事件
     */
    public void matchAndExecMessageEvent(MessageEvent<?> event) {
        for (YuniMessagePlugin plugin: messagePluginMap.values()) {
            // 检查插件是否被当前 BOT 实例订阅
            if (!pluginIsSubscribedByBot(plugin, ThreadLocalUtil.getBot().getId())) {
                continue;
            }
            // 检查当前插件是否被消息命中
            if (MessageMatcher.matchMessage(event, plugin)) {
                // 检查消息发送者是否有权调用该插件
                if (!checkPermission(event, plugin)) {
                    // 如果命中了，检查是否有权调用，如果无权调用，发出提示
                    replyNoPermission();
                    continue;
                }
                // 如果命中，执行插件
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
        EventDetector<?> detector = plugin.getDetector();
        try {
            // 反射执行插件入口函数
            runMethod.invoke(pluginBean, event, detector);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyNoPermission() {
        // TODO
    }

    /**
     * 检查消息发送者是否有权调用插件
     * @param event  消息事件
     * @param plugin  插件
     * @return  消息发送者是否有权调用插件
     */
    private static Boolean checkPermission(MessageEvent<?> event, YuniMessagePlugin plugin) {
        MessageSender sender = event.getSender();
        // TODO 再说吧
        return true;
    }

    ////////////////////////////////////
    // 下方为初始化部分代码
    ////////////////////////////////////

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

        // 设置插件 ID
        if (pluginAnno.id().isEmpty()) {
            // 以 Bean 的全名作为插件 ID
            yuniPlugin.setId(targetPluginBean.getClass().getName());
        } else {
            yuniPlugin.setId(pluginAnno.id());
        }

        // 设置插件的 Bean 本体
        yuniPlugin.setPluginBean(targetPluginBean);

        // 获取插件帮助信息
        String helpInfo = invokeBeanNoArgMethods(targetPluginBean, "helpInfo");
        yuniPlugin.setHelpInfo(helpInfo);

        // 获取插件默认订阅策略
        yuniPlugin.setSubmitCondition(pluginAnno.submit());

        // 根据插件默认订阅策略设置插件初始状态
        yuniPlugin.setIsSubscribed(yuniPlugin.getSubmitCondition().equals(SubmitConditions.YES));

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
            // 获取消息插件的入口方法
            yuniNegativePlugin.setRunMethod(targetPluginBean.getClass().getMethod("run", OneBotEvent.class, EventDetector.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 获取被动插件的事件探测器
        yuniNegativePlugin.setDetector(invokeBeanNoArgMethods(targetPluginBean, "detector"));

        // 如果被动插件由消息事件触发，继续构建
        if (targetPluginBean instanceof MessageCalledPluginBean) {
            buildFurtherForMessagePlugin(yuniNegativePlugin, (MessageCalledPluginBean<?>) targetPluginBean);
        }
    }

    /**
     * 从 YuniNegativePlugin 实例构建出 MessageCalledPluginBean 消息插件
     * @param yuniNegativePlugin YuniNegativePlugin 实例
     * @param targetPluginBean 插件 Bean 本体
     * @param <T> 泛型，限定入参实现 NegativePluginBean 接口
     */
    private <T extends MessageCalledPluginBean<?>> void buildFurtherForMessagePlugin(YuniNegativePlugin yuniNegativePlugin, T targetPluginBean) {
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

        // 构建结束（たぶん），将插件加入 negativePluginMap 中
        if (messagePluginMap.containsKey(yuniMessagePlugin.getId())) {
            throw new RuntimeException("插件 " + yuniMessagePlugin.getPluginBean().getClass().getName() +
                    " 的 ID " + yuniMessagePlugin.getId() + "已经存在!");
        }
        messagePluginMap.put(yuniMessagePlugin.getId(), yuniMessagePlugin);
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
     * 检查插件是否被当前 Bot 订阅了
     * @param plugin  插件
     * @param botId  Bot ID
     * @return  插件是否被该 Bot 订阅
     */
    private Boolean pluginIsSubscribedByBot(YuniPlugin plugin, Long botId) {
        // 检查插件是否被 Bot 订阅
        if (!plugin.getIsSubscribed()) {
            return false;
        }
        // TODO
        return true;
    }
}