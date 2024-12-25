package com.yuier.yuni.core.plugins.inner;

import com.yuier.yuni.common.anno.Plugin;
import com.yuier.yuni.common.detect.message.matchedout.order.OrderMatchedOut;
import com.yuier.yuni.common.detect.message.order.OrderDetector;
import com.yuier.yuni.common.detect.message.order.OrderOptionContainer;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.seg.ImageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.plugin.YuniPlugin;
import com.yuier.yuni.core.domain.pojo.request.PluginInfoPojo;
import com.yuier.yuni.common.interfaces.plugin.MessagePluginBean;
import com.yuier.yuni.common.utils.BotAction;
import com.yuier.yuni.common.utils.RedisCache;
import com.yuier.yuni.core.domain.pojo.request.PluginsInfoPicPojo;
import com.yuier.yuni.core.domain.pojo.response.GetPluginsInfoPicResPojo;
import com.yuier.yuni.core.randosoru.plugin.PluginManager;
import com.yuier.yuni.core.util.CallPythonServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.yuier.yuni.common.constants.SystemConstants.FILE_CACHE_MAP;
import static com.yuier.yuni.common.constants.SystemConstants.OBJECT_HASH_MAP;

/**
 * @Title: PluginManager
 * @Author yuier
 * @Package com.yuier.yuni.core.plugins.inner
 * @Date 2024/11/18 1:45
 * @description: 管理插件的插件
 */

@Plugin(name = "插件管理")
@Component
public class ManagePlugins implements MessagePluginBean<OrderDetector> {

    @Autowired
    PluginManager pluginManager;
    @Autowired
    RedisCache redisCache;
    @Autowired
    CallPythonServiceUtil pyServCaller;

    private MessageEvent<?> localEvent;

    private final String PLUGIN_MAP_HASH = "plugin:map:hash:";
    private final String PLUGIN_LIST_PIC_CACHE = "plugin:list:pic:cache:";

    private final String PLUGINS_CHECK = "check";

    @Override
    public OrderDetector detector() {
        return new OrderDetector.Builder()
                .setHead("插件")
                .addOption(
                        new OrderOptionContainer.OptionBuilder()
                                .setNameAndFlag(PLUGINS_CHECK, "查看")
                                .build()
                )
                .build();
    }

    @Override
    public void run(MessageEvent<?> event, OrderDetector detector) {
        localEvent = event;
        OrderMatchedOut orderMatchedOut = detector.getOrderMatchedOut();
        if (orderMatchedOut.optionExists(PLUGINS_CHECK)) {
            showPlugins();
        }
    }

    private void showPlugins() {
        // 应该先拼出一个当前位置插件信息的对象，然后再计算哈希
        // 然后再根据哈希决定是否使用缓存文件
        HashMap<Integer, PluginInfoPojo> pluginsInfoPojoMap  = new HashMap<>();
        for (Integer pluginId = 1; pluginId <= pluginManager.getTotalPluginNumber(); pluginId++) {
            YuniPlugin plugin = pluginManager.getPluginById(pluginId);
            PluginInfoPojo pluginInfoPicPojo = new PluginInfoPojo(
                    pluginId,
                    plugin.getName(),
                    pluginManager.pluginIsSubscribedAtThePosition(localEvent, plugin)
            );
            pluginsInfoPojoMap.put(pluginId, pluginInfoPicPojo);
        }
        // 计算当前插件信息集合的哈希
        Integer pojoMapHashCode = pluginsInfoPojoMap.hashCode();
        // 获取缓存中的哈希
        Map<String, Integer> objectHashCodeMap = redisCache.getCacheMap(OBJECT_HASH_MAP);
        // 每个位置下订阅情况不同，所以哈希也不同
        String positionStr = localEvent.getPosition().getMessageType().toString() + localEvent.getPosition().getPositionId();
        Integer pojoMapHashCodeInCache = objectHashCodeMap.getOrDefault(PLUGIN_MAP_HASH + positionStr, null);
        // 如果二者相等，且缓存中存在图片，使用缓存中的图片
        if (pojoMapHashCode.equals(pojoMapHashCodeInCache)) {
            // 尝试获取缓存中的图片
            Map<String, String> fileCacheMap = redisCache.getCacheMap(FILE_CACHE_MAP);
            // 对象存储中的文件
            String fileCache = fileCacheMap.getOrDefault(PLUGIN_LIST_PIC_CACHE + positionStr, null);
            if (fileCache != null) {
                BotAction.sendMessage(
                        localEvent.getPosition(),
                        new MessageSeg<?>[] {
                                new ImageSeg(fileCache)
                        }
                );
            }
        }
        // 二者不相等，或缓存中没有图片，请求 python 工具
        // 刷新哈希
        objectHashCodeMap.put(PLUGIN_MAP_HASH + positionStr, pojoMapHashCode);
        redisCache.setCacheMap(OBJECT_HASH_MAP, objectHashCodeMap);
        // 请求 python 服务
        GetPluginsInfoPicResPojo pluginsInfo = pyServCaller.getPluginsInfo(new PluginsInfoPicPojo(pluginsInfoPojoMap));
        System.out.println(pluginsInfo.getImage());
        // 刷新缓存
        // TODO
        // 发送图片
        BotAction.sendMessage(
                localEvent.getPosition(),
                new MessageSeg<?>[] {
                        // TODO
                        new ImageSeg("https://img2.baidu.com/it/u=402842293,2018792673&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500")
                }
        );
    }

    @Override
    public String helpInfo() {
        return "管理插件的插件";
    }
}
