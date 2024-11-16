package com.yuier.yuni.common.domain.bot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * @Title: YuniBot
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.bot
 * @Date 2024/11/16 16:34
 * @description: Bot 实例
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YuniBot {

    // Bot ID ，天然地应使用 QQ 号
    @JsonIgnore
    private Long id;

    // Bot 昵称
    @JsonProperty("nickname")
    private String nickName;

    // 对应的 OneBot 实现的 API 根路径
    @NonNull
    @JsonProperty("onebot-url")
    private String onebotUrl;
}
