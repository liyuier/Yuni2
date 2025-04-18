package com.yuier.yuni.common.domain.event.message.chain;

import cn.hutool.core.util.StrUtil;
import com.yuier.yuni.common.anno.JsonTypeDefine;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.AtData;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.MessageData;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import com.yuier.yuni.common.exceptions.parsecq.CQBracketNotClose;
import com.yuier.yuni.common.exceptions.parsecq.CQHeadWrongException;
import com.yuier.yuni.common.exceptions.parsecq.CQSegAttributeWrongException;
import com.yuier.yuni.common.exceptions.parsecq.CQSegTypeWrongException;
import com.yuier.yuni.common.utils.ThreadLocalUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * @Title: MessageChain
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.event.message.chain
 * @Date 2024/11/10 23:24
 * @description: 消息链
 */

@Slf4j
@Data
@AllArgsConstructor
public class MessageChain {

    private ArrayList<MessageSeg<?>> content;

    private HashMap<String, ArrayList<Integer>> segTypeIndexes;

    public MessageChain() {
        content = new ArrayList<>();
        segTypeIndexes = new HashMap<>();
    }

    public MessageChain(ArrayList<MessageSeg<?>> messageSegList) {
        this();
        content = messageSegList;
    }

    public MessageChain(MessageSeg<?>[] messageSegs) {
        this();
        content.addAll(Arrays.asList(messageSegs));
    }

    /**
     * 用于接收到消息，组装消息链时使用
     * 可以维护一个每种消息在 content 中的索引列表，很方便
     * @param messageSegList  原始消息事件中的消息段
     * @param buildSegTypeIndexes  是否维护 segTypeIndexes
     */
    public MessageChain(ArrayList<MessageSeg<?>> messageSegList, Boolean buildSegTypeIndexes) {
        this();
        for (int i = 0; i < messageSegList.size(); i++) {
            MessageSeg<?> seg = messageSegList.get(i);
            String type = seg.getType();
            content.add(seg);
            ArrayList<Integer> indexList = segTypeIndexes.getOrDefault(type, new ArrayList<>());
            indexList.add(i);
            segTypeIndexes.put(type, indexList);
        }
    }

    public MessageChain(String text) {
        this();
        content.add(new TextSeg(text));
    }

    public void addTextSeg(String text) {
        content.add(new TextSeg(
                new TextData(text)
        ));
    }

    public void add(MessageChain chain) {
        content.addAll(chain.getContent());
    }

    public Boolean startWithTextData() {
        return content.get(FIRST_INDEX).typeOf(MessageDataEnum.TEXT) &&
                !((TextSeg) content.get(FIRST_INDEX)).getData().getText().trim().isEmpty();
    }

    public Boolean startWithReplyData() {
        return content.get(FIRST_INDEX).typeOf(MessageDataEnum.REPLY);
    }

    /**
     * 获取开头的文本消息段
     * @return  开头的文本消息段
     */
    public TextData getStartTextData() {
        return ((TextSeg) content.get(FIRST_INDEX)).getData();
    }

    public Boolean containsMessageType(MessageDataEnum dataEnum) {
        return segTypeIndexes.containsKey(dataEnum.getTypeName());
    }

    /**
     * @return  是否包含 at 消息
     */
    public Boolean atSomeOne() {
        return containsMessageType(MessageDataEnum.AT);
    }

    /**
     * @return  是否包含 at bot 消息
     */
    public Boolean atBot() {
        if (!atSomeOne()) {
            return false;
        }
        ArrayList<Integer> atSegIndexes = segTypeIndexes.get(MessageDataEnum.AT.getTypeName());
        for (Integer index : atSegIndexes) {
            MessageSeg<AtData> seg = (MessageSeg<AtData>) content.get(index);
            if (Long.parseLong(seg.getData().getQq()) == ThreadLocalUtil.getBot().getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 消息链中是否包含指定字符串
     * @param str 指定字符串
     * @return 是否包含
     */
    public Boolean contains(String str) {
        for (MessageSeg<?> seg : this.content) {
            if (seg.typeOf(MessageDataEnum.TEXT)) {
                TextData data = (TextData) seg.getData();
                if (data.getText().contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换消息链中字符串元素
     * @param formerStr 原字符串
     * @param latterStr 新字符串
     */
    public void replace(String formerStr, String latterStr) {
        for (MessageSeg<?> seg : this.content) {
            if (seg.typeOf(MessageDataEnum.TEXT)) {
                TextData data = (TextData) seg.getData();
                data.setText(data.getText().replace(formerStr, latterStr));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (MessageSeg<?> seg : content) {
            str.append(seg.getData().toString());
        }
        return str.toString();
    }

    /**
     * 获取消息中的文本字符串
     * @return  消息中的文本字符串
     */
    public String getPlainText() {
        StringBuilder result = new StringBuilder();
        for (MessageSeg<?> seg : content) {
            if (seg instanceof TextSeg) {
                result.append(seg.getData().toString());
            }
        }
        return result.toString();
    }

    /**
     * @return  消息链种是否为空消息
     */
    public Boolean isEmpty() {
        return content.isEmpty();
    }

    /**
     * 接收一个 CQ 码字符串，组装为消息链
     * @param cqCodeStr  CQ 码字符串
     * @return  组装出的消息链
     */
    public static MessageChain parseCQToChain(String cqCodeStr) {
        MessageChain chain = new MessageChain();
        try {
            chain.setContent(parseCQToArray(cqCodeStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain;
    }

    /**
     * 解析 CQ 码的算法
     * @param cqCode  CQ 码字符串
     * @return  解析出的消息数组
     */
    private static <S extends MessageData, T extends MessageSeg<S>> ArrayList<MessageSeg<?>> parseCQToArray(String cqCode) {
        ArrayList<MessageSeg<?>> messageSegs = new ArrayList<>();
        // 遍历 CQ 码字符串
        int cqCodeLength = cqCode.length();
        T exactMessageSeg = null;
        int curIndex = 0;
        while(curIndex < cqCodeLength) {
            char curChar = cqCode.charAt(curIndex);
            // 遇到 CQ 头，开始解析消息段
            if (curChar == '[') {
                // 检查后两位是否为 CQ
                if (curIndex <= cqCodeLength - 4 &&
                    cqCode.charAt(curIndex + 1) == 'C' &&
                    cqCode.charAt(curIndex + 2) == 'Q' &&
                    cqCode.charAt(curIndex + 3) == ':') {
                    int cqHeadIndex = curIndex;
                    // 解析消息段类型
                    curIndex += 4;
                    if (curIndex >= cqCodeLength) {
                        throw new CQSegTypeWrongException(cqCode, curIndex);
                    }
                    int tmpIndex = curIndex;
                    // 提取消息段类型字符串
                    StringBuilder messageType = new StringBuilder();
                    while (cqCode.charAt(curIndex) != ',') {
                        messageType.append(cqCode.charAt(curIndex));
                        curIndex ++;
                    }
                    boolean canMatchValidType = false;
                    // 匹配消息段类型，如果能匹配上，就进行建构
                    for (MessageDataEnum dataEnum : MessageDataEnum.values()) {
                        if (messageType.toString().equals(dataEnum.toString())) {
                            canMatchValidType = true;
                            /* 实例化匹配到的消息段 */
                            // 获取子消息段实例
                            exactMessageSeg = getExactMessageSeg(String.valueOf(messageType));
                            // 解析消息 data 参数键值对
                            // 跳过逗号
                            curIndex ++;
                            if (curIndex >= cqCodeLength) {
                                throw new CQSegAttributeWrongException(cqCode, curIndex);
                            }
                            tmpIndex = curIndex;
                            // 遍历解析消息字段属性键值对
                            while (curIndex < cqCodeLength &&
                                    cqCode.charAt(curIndex) != ']') {
                                // 解析键
                                StringBuilder attributeName = new StringBuilder();
                                while (curIndex < cqCodeLength &&
                                        cqCode.charAt(curIndex) != '='){
                                    attributeName.append(cqCode.charAt(curIndex));
                                    curIndex ++;
                                }
                                // 跳过等于号
                                curIndex ++;
                                if (curIndex >= cqCodeLength ||
                                    cqCode.charAt(curIndex) == ']') {
                                    throw new CQSegAttributeWrongException(cqCode, tmpIndex);
                                }
                                tmpIndex = curIndex;
                                // 解析值
                                StringBuilder attributeValue = new StringBuilder();
                                while (curIndex < cqCodeLength &&
                                        cqCode.charAt(curIndex) != ',' &&
                                        cqCode.charAt(curIndex) != ']') {
                                    if (cqCode.charAt(curIndex) == '&') {
                                        /* 可能需要转义 */
                                        StringBuilder transTarget = new StringBuilder();
                                        // 检查是否可以转义
                                        Boolean canTrans = tryParseTransText(cqCode, curIndex, transTarget);
                                        if (canTrans) {
                                            // 如果可以转义，将转义后的字符加入结果，并继续遍历。
                                            attributeValue.append(transTarget);
                                            curIndex += 5;
                                            continue;
                                        }
                                    }
                                    attributeValue.append(cqCode.charAt(curIndex));
                                    curIndex ++;
                                }
                                // 通过反射，将获取的键值对组装进消息段的 data 属性中
                                S segData = exactMessageSeg.getData();
                                Class<S> segDataClazz = (Class<S>) segData.getClass();
                                try {
                                    Field attributeField = segDataClazz.getDeclaredField(StrUtil.toCamelCase(attributeName.toString()));
                                    attributeField.setAccessible(true);
                                    attributeField.set(segData, attributeValue.toString());
                                } catch (NoSuchFieldException | IllegalAccessException e) {
                                    log.error("在 " + exactMessageSeg.getType() + " 类型下，" +
                                                "data 没有 " + attributeName +  " 属性。" +
                                                "原始 CQ 字符串：" + cqCode + " ; " +
                                                "属性开始处索引: " + tmpIndex);
                                }
                                // 如果当前索引处为 `,` , 说明当前消息段还存在参数需解析。跳过逗号。
                                if (cqCode.charAt(curIndex) == ',') {
                                    curIndex ++;
                                }
                            }
                            // 建构完毕，跳出对消息段类型的匹配
                            break;
                        }
                    }
                    if (!canMatchValidType) {
                        throw new CQSegTypeWrongException(cqCode, tmpIndex);
                    }
                    // 建构完毕，此处字符应为 `]`
                    if (cqCode.charAt(curIndex) != ']') {
                        throw new CQBracketNotClose(cqCode, cqHeadIndex);
                    }
                    // 解析出一个消息段，将其加入返回结果中
                    if (exactMessageSeg != null) {
                        messageSegs.add(exactMessageSeg);
                        exactMessageSeg = null;
                        // 跳过 CQ 段的结尾 `]`
                        curIndex ++;
                    }
                } else {
                    throw new CQHeadWrongException(cqCode, curIndex);
                }
            } else {
                // 上文中，在遇到 CQ 头后，已经直接将对应的消息段组装了出来
                // 所以这里一定是文本消息。
                TextSeg textSeg = new TextSeg();
                textSeg.setType(MessageDataEnum.TEXT.toString());
                StringBuilder text = new StringBuilder();
                while (curIndex < cqCodeLength &&
                        cqCode.charAt(curIndex) != '[') {
                    if (cqCode.charAt(curIndex) == '&') {
                        /* 可能需要转义 */
                        StringBuilder transTarget = new StringBuilder();
                        // 检查是否可以转义
                        Boolean canTrans = tryParseTransText(cqCode, curIndex, transTarget);
                        if (canTrans) {
                            // 如果可以转义，将转义后的字符加入结果，并继续遍历。
                            text.append(transTarget);
                            curIndex += 5;
                            continue;
                        }
                    }
                    text.append(cqCode.charAt(curIndex));
                    curIndex ++;
                }
                textSeg.setData(new TextData(text.toString()));
                messageSegs.add(textSeg);
            }
        }
        return messageSegs;
    }

    /**
     * 尝试解析转义字符
     * @param cqCode  原始 CQ 码
     * @param curIndex  遇到 & 字符的位置
     * @param transTarget  如果能转义成功，转义结果
     * @return  是否转义成功
     */
    private static Boolean tryParseTransText(String cqCode, int curIndex, StringBuilder transTarget) {
        // 如果索引不足以得到 5 字符长度的转义字符串，返回 false
        if (curIndex > cqCode.length() - 5) {
            return false;
        }
        // 获取当前位置开始的 5 个字符
        StringBuilder tmpText = new StringBuilder();
        for (int i = 0; i < 5; i ++) {
            tmpText.append(cqCode.charAt(curIndex + i));
        }
        boolean needTrans = false;
        // 直接匹配 4 种转义符
        switch (tmpText.toString()) {
            case "&amp;":
                transTarget.append("&");
                needTrans = true;
                break;
            case "&#91;":
                transTarget.append("[");
                needTrans = true;
                break;
            case "&#93;":
                transTarget.append("]");
                needTrans = true;
                break;
            case "&#44;":
                transTarget.append(",");
                needTrans = true;
                break;
            default:
                break;
        }
        return needTrans;
    }

    /**
     * 根据消息段类型获取确切的消息段子类
     * @param messageType  消息段类型
     * @return  初步实例化的子消息实例
     * @param <T>  MessageSeg 子类型
     */
    private static <S extends MessageData, T extends MessageSeg<S>> T getExactMessageSeg(String messageType) {
        HashMap<String, T> subMessageSegMap = new HashMap<>();
        // 初始化 map
        if (subMessageSegMap.isEmpty()) {
            // 获取 MessageSeg 的所有子类
            String packagePath = "com.yuier.yuni.common.domain.event.message.chain.seg";
            Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packagePath));
            Set<Class<? extends MessageSeg>> subTypesOfMessageSeg = reflections.getSubTypesOf(MessageSeg.class);
            for (Class<? extends MessageSeg> subType : subTypesOfMessageSeg) {
                // 注册
                // 跳过接口和抽象类
                if(subType.isInterface() || Modifier.isAbstract(subType.getModifiers())){
                    continue;
                }
                // 获取消息段子类上的 JsonTypeDefine 注解的值
                JsonTypeDefine typeName = subType.getAnnotation(JsonTypeDefine.class);
                String typeId = (typeName != null) ? typeName.value() : subType.getSimpleName().toLowerCase();
                if (messageType.equals(typeId)) {
                    try {
                        // 实例化子类并放入 map 中
                        T subMessageSeg = (T) subType.getConstructor().newInstance();
                        subMessageSeg.setType(messageType);
                        subMessageSegMap.put(messageType, subMessageSeg);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return subMessageSegMap.getOrDefault(messageType, null);
    }
}
