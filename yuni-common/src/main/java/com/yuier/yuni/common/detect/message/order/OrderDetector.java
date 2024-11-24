package com.yuier.yuni.common.detect.message.order;

import com.yuier.yuni.common.detect.message.matchedout.order.OrderArgMatchedOut;
import com.yuier.yuni.common.detect.message.matchedout.order.OrderMatchedOut;
import com.yuier.yuni.common.detect.message.matchedout.order.OrderOptionMatchedOut;
import com.yuier.yuni.common.domain.event.message.MessageEvent;
import com.yuier.yuni.common.domain.event.message.chain.MessageChain;
import com.yuier.yuni.common.domain.event.message.chain.MessageChainForOrder;
import com.yuier.yuni.common.domain.event.message.chain.seg.AtSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.MessageSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.ReplySeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.TextSeg;
import com.yuier.yuni.common.domain.event.message.chain.seg.data.TextData;
import com.yuier.yuni.common.enums.MessageDataEnum;
import com.yuier.yuni.common.enums.OrderArgAcceptType;
import com.yuier.yuni.common.interfaces.detector.MessageDetector;
import com.yuier.yuni.common.utils.BotAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.yuier.yuni.common.constants.SystemConstants.BLANK_SPACE;
import static com.yuier.yuni.common.constants.SystemConstants.FIRST_INDEX;

/**
 * @Title: OrderDetector
 * @Author yuier
 * @Package com.yuier.yuni.common.detect
 * @Date 2024/11/9 16:36
 * @description: 指令消息探测器
 * @Detail
 * 一条指令分为指令头 OrderHead、指令参数 OrderArgContainer、指令选项 OrderOptions。三者均实现指令段 OrderElement 接口
 * 指令头
 *   一条指令的入口，最佳实践中，每条指令的指令头是唯一的
 * 指令参数
 *   跟在指令头后面，可以携带用户输入的信息，分为必选参数与可选参数。指令可以不定义指令参数
 * 指令选项
 *   可以视作对指令参数的扩展，某种意义上是一条“微型指令”
 * 举例说明
 *   现在我要定义一条发色图的指令 /色图 {数量} {最高数量} -标签 <标签> -合并  对于上述指令
 *   /色图 就是指令头，探测到消息开头为该字符串，进入色图指令的解析逻辑
 *   {数量}, {最高数量} 均为可选参数。
 *      用户不输入这两个参数时，功能开发者可自行设置默认数量；
 *      用户仅输入一个参数，视作需要的数量；
 *      用户输入两个参数，视作需要两个参数之间的任意数量色图。
 *    -标签 <标签> 是指令选项。其中 -标签 是选项名称，<标签> 是选项参数，且为必选参数
 *      用户可以不输入 -标签 选项名称，但如果输入该选项名，后面必须跟上选项参数
 *    -合并 也是指令选项，该指令选项没有设定指令参数。输入该选项名，表示需要将色图合并在转发消息中发出
 * 更详细的说明见每个指令元素的实现代码
 */

@Data
@Component
@AllArgsConstructor
public class OrderDetector implements MessageDetector {

    /**
     * 指令头
     */
    private OrderHead head;

    /**
     * 指令参数
     * 跟在指令头后面。可选参数必须在必选参数后面
     */
    private OrderArgContainer orderArgs;

    /**
     * 指令选项
     * 每个选项都是可选的，其结构分为选项标识与选项参数
     */
    private OrderOptionContainer orderOptions;

    /**
     * 由消息探测器解析出来的玩意
     */
    private OrderMatchedOut orderMatchedOut;

    public OrderDetector() {

    }

    public static class Builder {
        private OrderHead head;
        private OrderArgContainer args;
        private OrderOptionContainer options;

        public Builder() {
            this.head = new OrderHead();
            this.args = new OrderArgContainer();
            this.options = new OrderOptionContainer();
        }

        public Builder setHead(String headName) {
            this.head = new OrderHead(headName);
            return this;
        }

        public Builder addRequiredArg(String name) {
            args.addRequiredArg(name);
            return this;
        }

        public Builder addRequiredArg(String argName, OrderArgAcceptType contentType) {
            args.addRequiredArg(argName, contentType);
            return this;
        }

        public Builder addRequiredArg(String argName, String helpInfo) {
            args.addRequiredArg(argName, helpInfo);
            return this;
        }

        public Builder addRequiredArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            args.addRequiredArg(argName, contentType, helpInfo);
            return this;
        }

        public Builder addRequiredArg(RequiredArg arg) {
            args.addRequiredArg(arg);
            return this;
        }

        public Builder addOptionalArg(String argName) {
            args.addOptionalArg(argName);
            return this;
        }

        public Builder addOptionalArg(String argName, OrderArgAcceptType contentType) {
            args.addOptionalArg(argName, contentType);
            return this;
        }

        public Builder addOptionalArg(String argName, String helpInfo) {
            args.addOptionalArg(argName, helpInfo);
            return this;
        }

        public Builder addOptionalArg(String argName, OrderArgAcceptType contentType, String helpInfo) {
            args.addOptionalArg(argName, contentType, helpInfo);
            return this;
        }

        public Builder addOptionalArg(OptionalArg arg) {
            args.addOptionalArg(arg);
            return this;
        }

        public Builder addOption(OrderOption option) {
            options.addOption(option);
            return this;
        }

        public OrderDetector build() {
            return new OrderDetector(this.head, this.args, this.options, new OrderMatchedOut());
        }
    }

    @Override
    public Boolean hit(MessageEvent<?> event) {
        // 清理 options 状态
        cleanDetectorStat();
        // 将消息链转为方便指令探测器使用的 MessageChainForOrder
        MessageChainForOrder chainForOrder = splitMessageForOrder(event.getMessageChain());
        orderMatchedOut = new OrderMatchedOut();
        String orderMark = getOrderMark();
        // 开始匹配，检查一些基本的信息
        // 如果消息链不是以有效文本开头，直接判不匹配
        if (!chainForOrder.startWithTextData()) {
            return false;
        }
        /*
          检查消息链开头的文本消息段能否匹配指令
          即开头消息段是否等于 指令标识符 + 指令头
         */
        TextData startTextData = chainForOrder.getStartTextData();
        if (!startTextData.getText().equals(orderMark + head.getName())) {
            return false;
        }
        // 如果拆分出来的消息段数量不能满足指令的最低要求，不匹配
        if (chainForOrder.getContent().size() < orderLeastSegNum()) {
            return false;
        }
        // 基本信息检查结束，现在开始正式匹配

        /*
         * 匹配参数与选项
         * 思路：
         * 1. 先匹配必要参数，所有必要参数都要能匹配上才能进入后续步骤
         * 2. 必要参数匹配结束，尝试匹配可选参数
         * 3. 最后匹配指令选项
         * 匹配过程采用非贪婪匹配，即如果某段文本消息与选项标识匹配，那么就停止匹配可选参数，开始匹配该选项
         */

        // 由于上文已经检查了消息的第一个消息段可以匹配指令头，所以此时从第二个消息段开始匹配
        chainForOrder.setCurSegIndex(FIRST_INDEX + 1);

        // 匹配必选参数，如果匹配不上，直接返回 false
        ArrayList<OrderArgMatchedOut> argMatchedList = new ArrayList<>();
        if (!matchRequiredArgs(orderArgs.getRequiredArgList(), chainForOrder, argMatchedList)) {
            return false;
        }
        // 如果 cfo 消息段检查完毕，返回 true
        if (chainForOrder.messageSegsMatchedEnd()) {
            // 将匹配出的必选参数放入 orderMatchedOut 中
            orderMatchedOut.addOrderArgsMatchedList(argMatchedList);
            return true;
        }

        // 匹配可选参数
        // 可选参数的匹配失败不会直接导致整个匹配逻辑的失败，因此这里定义的方法为 void 类型
        matchOptionalArgs(orderArgs.getOptionalArgList(), chainForOrder, argMatchedList);
        // 将匹配出的可选参数放入 orderMatchedOut 中
        orderMatchedOut.addOrderArgsMatchedList(argMatchedList);
        // 如果 cfo 消息段检查完毕，返回 true
        if (chainForOrder.messageSegsMatchedEnd()) {
            return true;
        }

        /*
         * 指令参数匹配完毕，开始匹配选项
         * 匹配选项的原则是完美匹配，即
         * 如果 cfo 中存在未匹配完的消息段，而这些剩余消息段无法匹配任意一条选项，则返回 false
         * 只有在下面的阶段中存在某种情况，使选项正好将消息匹配完，才可以返回 true
         */

        // 匹配选项，如果返回 false，直接返回 false
        ArrayList<OrderOptionMatchedOut> optionMatchedList = new ArrayList<>();
        if (!matchOptions(orderOptions.getOptionList(), chainForOrder, optionMatchedList)) {
            return false;
        } else {
            // OHHHHHHH!!! BRO YOU DID DO IT!!!
            // 将匹配出的选项集合放入 orderMatchedOut 中
            orderMatchedOut.addOrderOptionsMatchedOut(optionMatchedList);
            return true;
        }
    }

    /**
     * 匹配选项
     * @param optionList  指令中定义的待匹配的选项
     * @param chainForOrder  MessageChainForOrder 实例
     * @param optionMatchedList  用于临时储存被匹配出的选项消息链
     * @return  是否可以完美匹配
     */
    public Boolean matchOptions(ArrayList<OrderOption> optionList,
                                MessageChainForOrder chainForOrder,
                                ArrayList<OrderOptionMatchedOut> optionMatchedList) {
        for (OrderOption option : optionList) {
            // 如果 cfo 消息段检查完毕，直接返回 true
            if (chainForOrder.messageSegsMatchedEnd()) {
                return true;
            }

            // 如果本选项已经与消息段的某部分匹配上，匹配下一条选项。
            if (option.getMatched()) {
                continue;
            }

            // 如果当前消息段不是文本消息段，说明无法与任意一条选项匹配，返回 false
            if (!chainForOrder.getCurMessageSeg().typeOf(MessageDataEnum.TEXT)) {
                return false;
            }
            // 消息链过短，无法匹配当前选项，匹配下一条选项。
            if (chainForOrder.restMessageSegNum() < option.leastMessageSegNum()) {
                continue;
            }
            // 当前消息段与选项标识不匹配，匹配下一条选项。
            String text = ((TextSeg) chainForOrder.getCurMessageSeg()).getData().getText();
            if (!text.equals(option.getFlag())) {
                continue;
            }

            // 保存原始索引，以供匹配不上当前选项时回滚状态
            int rawCurSegIndex = chainForOrder.getCurSegIndex();
            // 先准备好一个 orderOptionMatchedOut
            OrderOptionMatchedOut orderOptionMatchedOut = new OrderOptionMatchedOut(option.getName(), option.getHelpInfo());

            // 当前消息段与选项标识匹配，尝试匹配整条选项。消息段指针右移一位
            chainForOrder.curSegIndexStepForwardBy(1);

            // 匹配必选参数，如果匹配不上，恢复现场，进入下一轮循环
            ArrayList<OrderArgMatchedOut> argMatchedList = new ArrayList<>();
            if (!matchRequiredArgs(option.getRequiredArgs(), chainForOrder, argMatchedList)) {
                // 恢复消息段指针
                chainForOrder.setCurSegIndex(rawCurSegIndex);
                continue;
            }
            // 如果 cfo 消息段检查完毕，返回 true
            if (chainForOrder.messageSegsMatchedEnd()) {
                // 将上文匹配出的必选参数放入 optionMatchedOut 中
                orderOptionMatchedOut.addOrderArgsMatchedList(argMatchedList);
                // 将 optionMatchedOut 加入 optionMatchedList 中
                optionMatchedList.add(orderOptionMatchedOut);
                // 标记本选项已经与某段消息匹配
                option.setMatched(true);
                return true;
            }

            // 匹配可选参数
            matchOptionalArgs(option.getOptionalArgs(), chainForOrder, argMatchedList);
            // 将匹配出的可选参数加入 optionMatchedOut 中
            orderOptionMatchedOut.addOrderArgsMatchedList(argMatchedList);
            // 将 optionMatchedOut 加入 optionMatchedList 中
            optionMatchedList.add(orderOptionMatchedOut);
            // 标记本选项已经与某段消息匹配
            option.setMatched(true);

            // 如果匹配完了，返回 true
            if (chainForOrder.messageSegsMatchedEnd()) {
                return true;
            } else {
                // 如果没有匹配完，递归匹配剩余部分消息段与剩余选项
                return matchOptions(optionList, chainForOrder, optionMatchedList);
            }
        }
        // 所有选项匹配结束，如果消息段正好匹配完，返回 true，否则返回 false
        return chainForOrder.messageSegsMatchedEnd();
    }

    /**
     * 匹配可选参数
     * @param optionalArgs  待匹配的可选参数
     * @param chainForOrder  MessageChainForOrder 实例
     * @param argMatchedList  用于临时储存被匹配出的参数消息段
     */
    private void matchOptionalArgs(ArrayList<OptionalArg> optionalArgs,
                                      MessageChainForOrder chainForOrder,
                                      ArrayList<OrderArgMatchedOut> argMatchedList) {

        /*
         * 采用 “先到先得，过时不候” 的方式匹配可选参数
         * 具体来说，使用指针单向遍历消息段，并使用 for 循环遍历可选参数。
         * 对于每个消息段，遍历每个可选参数，将消息段匹配给第一个可匹配的参数，随后消息段指针右移，for 循环也进入下一轮，可选参数不回头遍历。
         * 举例来说：
         * 有如下 4 个可选参数：甲-at、乙-number、丙-at、丁-number
         * 有如下消息段：<123>、<@1>、<@2>
         * 也许会有读者认为应该如此匹配：<123>-乙，<@1>-甲，<@2>-丙，但实际并非如此。
         * 1. 对于消息段 <123>，从甲开始遍历可选参数，第一轮搜索匹配上了乙，消息段指针右移；参数从丙开始遍历
         * 2. 对于消息段 <@1>，从丙开始查找接收 @ 消息的可选参数，匹配上了丙
         * 3. 对于消息段 <@2>，从丁开始查找接收 @ 消息的可选参数，找不到
         * 最终结果为：<123>-乙，<@1>-丙，<@2>-未匹配。
         * 这样的匹配原则规则明确，方便理解。
         *
         * 鉴于以上 feature，我推荐这样的指令定义策略：
         * 1. 不将不同类型的可选参数混杂定义，如上方示例，at 类参数与 number 类参数混杂定义，极易出现不符合预期的匹配结果。
         * 2. 不定义连续多个可选参数，例如 3 个以上。超过 3 个可选参数，很难说清自己接收到的数据到底是不是用户想要传达的意图。
         * 3. 多用 选项+必选参数 的模式代替可选参数。实际上这样的模式可以理解为一种 “有名字的可选参数”，其匹配逻辑更精准，代码更健壮。
         */

        // 遍历所有可选参数，检查是否可以与剩余的某个消息段匹配上
        for (OptionalArg optionalArg : optionalArgs) {
            /*
             * 与必选参数不同，一段消息可能无法匹配所有可选参数，因此这里需要检查消息段指针是否越界
             * 如果 cfo 消息段检查完毕，返回 true
             */
            if (chainForOrder.messageSegsMatchedEnd()) {
                return;
            }

            // 取出当前消息段
            MessageSeg<?> curMessageSeg = chainForOrder.getCurMessageSeg();

            /*
             * 非贪婪匹配的实现
             * 如果匹配非必选参数时，匹配到某个选项的标识，那么跳出匹配
             */

            // 检查消息段是否为文本消息段，如果是，那么可能会与某个选项标识匹配上
            if (curMessageSeg.typeOf(MessageDataEnum.TEXT)) {
                String text = ((TextSeg) curMessageSeg).getData().getText();
                if (orderOptions.hasFlag(text)) {
                    // 当前消息段匹配上了某个选项标识，退出匹配
                    return;
                }
            }

            // 如果当前参数匹配回复消息段，需要特殊处理
            // 俺寻思直接复制底下匹配必需参数那段问题不大
            if (optionalArg.wantsMessageType(OrderArgAcceptType.REPLY)) {
                // 如果 cfo 中没有储存回复消息段，直接进入下一轮循环
                if (!chainForOrder.storesReplyData()) {
                    continue;
                }
                // 如果 cfo 中储存了回复消息，该参数直接匹配上
                // TODO @ 消息参数应予以扩展，允许指定目标
                // 在 matchedOut 中添加一个回复消息段
                /*
                 * TODO 顺便，之前想到如果指令中定义多个回复消息参数，这部分逻辑会出问题
                 *  但是今晚上灵光一现，实际上 QQ 消息中不会存在多条回复消息
                 *  因此优秀的用户应当自己学会确保自己的定义中不要出现多个回复消息参数  ~~绝对不是因为我懒得写校验逻辑~~
                 */
                argMatchedList.add(new OrderArgMatchedOut(
                        optionalArg.getName(),
                        OrderArgAcceptType.REPLY,
                        chainForOrder.getReplyData(),
                        optionalArg.getHelpInfo()
                ));
                // 总消息段指针，不动！
                // 进入下一轮循环，依然对于当前消息段，检查下一个参数
                continue;
            } else {
                // 如果非必选参数匹配不上当前消息段，进入下一轮循环，匹配下一个可选参数
                if (!messageSegMatchesArg(curMessageSeg, optionalArg)) {
                    continue;
                }
            }
            // 当前参数不期望回复消息，并且可以与当前消息段匹配上
            // 将提取出来的参数保存下来
            argMatchedList.add(new OrderArgMatchedOut(
                    optionalArg.getName(),
                    optionalArg.getAcceptType(),
                    curMessageSeg.getData(),
                    optionalArg.getHelpInfo()
            ));
            // 将消息段指针向右移动一位
            chainForOrder.curSegIndexStepForwardBy(1);
        }
    }

    @Override
    public Boolean defineValid() {
        return head.valid() &&
                orderArgs.valid() &&
                orderOptions.valid();
    }

    /**
     * 匹配必选参数
     * @param requiredArgs  待匹配的必要参数
     * @param chainForOrder  MessageChainForOrder 实例
     * @param argMatchedList  用于临时储存被匹配出的参数消息段
     * @return  是否可以匹配上
     */
    private Boolean matchRequiredArgs(ArrayList<RequiredArg> requiredArgs,
                                      MessageChainForOrder chainForOrder,
                                      ArrayList<OrderArgMatchedOut> argMatchedList) {
        // 遍历所有必选参数，检查是否可以与消息段按顺序匹配上
        // 因为在 hit 函数前半部分检查过参数数量必然能满足最低要求，因此这里不需要考虑消息段指针越界问题
        for (RequiredArg requiredArg : requiredArgs) {
            // 取出当前消息段
            MessageSeg<?> curMessageSeg = chainForOrder.getCurMessageSeg();
            // 如果当前参数期望回复消息段，需要特殊处理
            if (requiredArg.wantsMessageType(OrderArgAcceptType.REPLY)) {
                // 如果 cfo 中没有储存回复消息段，直接返回不匹配
                if (!chainForOrder.storesReplyData()) {
                    return false;
                }
                // 如果 cfo 中储存了回复消息，该参数直接匹配上
                // TODO @ 消息参数应予以扩展，允许指定目标
                // 在 matchedOut 中添加 cfo 中储存的回复消息
                argMatchedList.add(new OrderArgMatchedOut(
                        requiredArg.getName(),
                        OrderArgAcceptType.REPLY,
                        chainForOrder.getReplyData(),
                        requiredArg.getHelpInfo()
                ));
                /*
                 * 上一步中 requiredArg 匹配的并非 curMessageSeg, 而是 chainForOrder 中储存的 replyData
                 * 因此 curMessageSeg 并未使用，于是这里的消息段指针不需要移动，直接进入下一轮循环
                 * 继续基于当前消息段匹配必要参数
                 */
                continue;
            } else {
                // 如果必选参数匹配不上当前消息段，返回 false
                if (!messageSegMatchesArg(curMessageSeg, requiredArg)) {
                    return false;
                }
            }
            // 走到这一步，说明当前消息段可以匹配上当前必要参数，且参数不期望接收回复消息段。将匹配出的参数保存下来
            argMatchedList.add(new OrderArgMatchedOut(
                    requiredArg.getName(),
                    requiredArg.getAcceptType(),
                    curMessageSeg.getData(),
                    requiredArg.getHelpInfo()
            ));
            // 将消息段指针向右移动一位
            chainForOrder.curSegIndexStepForwardBy(1);
        }
        // 所有必要参数均成功匹配结束，返回 true
        return true;
    }

    /**
     * 判断消息段是否可以为参数所匹配
     * @param messageSeg  消息段
     * @param arg  指令参数
     * @return  消息段是否可以为参数所匹配
     */
    private Boolean messageSegMatchesArg(MessageSeg<?> messageSeg, OrderArg arg) {
        final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
        switch (arg.getAcceptType()) {
            case TEXT -> {
                return messageSeg.typeOf(MessageDataEnum.TEXT);
            }
            case NUMBER -> {
                if (!messageSeg.typeOf(MessageDataEnum.TEXT)) {
                    return false;
                }
                String segText = ((TextSeg) messageSeg).getData().getText();
                return NUMBER_PATTERN.matcher(segText).matches();
            }
            case AT -> {
                return messageSeg.typeOf(MessageDataEnum.AT);
            }
            case IMAGE -> {
                return messageSeg.typeOf(MessageDataEnum.IMAGE);
            }
            case URL -> {
                if (!messageSeg.typeOf(MessageDataEnum.TEXT)) {
                    return false;
                }
                String segText = ((TextSeg) messageSeg).getData().getText();
                return isValidUrl(segText);
            }
            case REPLY -> {
                return messageSeg.typeOf(MessageDataEnum.REPLY);
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * 检查一段字符串是否为 URL
     * @param url  待检查的字符串
     * @return  是否为合法 URL
     */
    private Boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 要能匹配上指令，消息要求能拆分成的最少消息段数量
     * 数量为：消息头 1 段加上必要参数数量
     * @return  能匹配上指令的最短消息段数量
     */
    private Integer orderLeastSegNum() {
        return 1 + orderArgs.getRequiredArgNum();
    }

    /**
     * 获取指令标识符
     * @return  指令标识符
     */
    private String getOrderMark() {
        // TODO 这里暂时就直接返回一个反斜杠
        return "/";
    }

    /**
     * 将 MessageChain 解析为适合指令消息探测器使用的结构
     * 思路为将每个消息段单独提出
     * 对于文本消息段，如果存在空格，以空格为分割符，将文本消息段分为多段
     * @param chain 消息链
     * @return MessageChainForOrder
     */
    private static MessageChainForOrder splitMessageForOrder(MessageChain chain) {
        /*
         * TODO 此处可优化，每个请求维护一个 cfo 即可
         *  ThreadLocal, 启动！
         */
        MessageChainForOrder chainForOrder = new MessageChainForOrder();
        // 遍历消息段
        for (MessageSeg<?> messageSeg : chain.getContent()) {
            // 如果消息段为文本消息段
            if (messageSeg.typeOf(MessageDataEnum.TEXT)) {
                // 将文本消息以空格为分割符，拆分为多段
                String[] strArr = ((TextSeg) messageSeg).getData().getText().split(BLANK_SPACE);
                for (String str : strArr) {
                    if (!str.trim().isEmpty()) {
                        chainForOrder.addTextSeg(str);
                    }
                }
            } else {
                chainForOrder.getContent().add(messageSeg);
            }
        }
        /* 如果存在回复消息，进行额外处理
          1. 将头部的回复类消息拆下，保存在 chainForOrder 中的一个单独的变量中
          2. 删除一个回复消息自带的 @ 消息
         */
        if (chainForOrder.startWithReplyData()) {
            // 将回复消息段拆下，存入 chainForOrder 的 replyData 字段中
            chainForOrder.setReplyData(((ReplySeg) chainForOrder.getContent().remove(FIRST_INDEX)).getData());
            // 获取回复的目标消息的发送者的 ID, 以供后续删除对应的 @ 消息
            Long userId = BotAction.getReplayTargetSenderId(Long.valueOf(chainForOrder.getReplyData().getId()));
            // 遍历后续消息段
            for (MessageSeg<?> messageSeg : chainForOrder.getContent()) {
                // 如果发现了与回复消息相匹配的 @ 消息
                if (messageSeg.typeOf(MessageDataEnum.AT) &&
                        ((AtSeg) messageSeg).getData().getQq().equals(String.valueOf(userId))) {
                    // 将此 @ 消息删除
                    chainForOrder.getContent().remove(messageSeg);
                    break;
                }
            }
        }
        return chainForOrder;
    }

    /**
     * 对于每条新的请求，做一些状态上的清理
     */
    private void cleanDetectorStat() {
        // 每条选项都暂未与当前新消息的任意部分匹配
        for (OrderOption option : orderOptions.getOptionList()) {
            option.setMatched(false);
        }
    }
}
