package com.yuier.yuni.common.randosoru.plugin;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.domain.event.OneBotEvent;
import com.yuier.yuni.common.domain.plugin.YuniMessagePlugin;
import com.yuier.yuni.common.domain.plugin.YuniNegativePlugin;
import com.yuier.yuni.common.domain.plugin.YuniPlugin;
import com.yuier.yuni.common.interfaces.detector.EventDetector;
import com.yuier.yuni.common.interfaces.plugin.MessageCalledPluginBean;
import com.yuier.yuni.common.interfaces.plugin.NegativePluginBean;
import com.yuier.yuni.common.interfaces.plugin.PluginBean;
import com.yuier.yuni.common.utils.BeanCopyUtils;
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

    // 被动类插件集合
    private HashMap<String, YuniNegativePlugin> negativePluginMap;

    public PluginManager() {
        negativePluginMap = new HashMap<>();
    }

    // 初始化
    public void initialize() {
        // 扫描所有加了 @PluginBean 注解的插件
        Map<String, Object> pluginBeans = applicationContext.getBeansWithAnnotation(Plugin.class);
        for (Object bean: pluginBeans.values()) {
            if (bean instanceof PluginBean) {
                buildYuniPluginFromPluginBean((PluginBean) bean);
            }
        }
        System.out.println("OK");
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
        negativePluginMap.put(yuniMessagePlugin.getId(), yuniMessagePlugin);
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
}
