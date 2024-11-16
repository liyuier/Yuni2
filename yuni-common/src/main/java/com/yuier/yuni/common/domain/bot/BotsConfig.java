package com.yuier.yuni.common.domain.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Title: BotsConfig
 * @Author yuier
 * @Package com.yuier.yuni.common.domain.bot
 * @Date 2024/11/16 17:20
 * @description: 解析并获取整个 bots.yml
 * Codes wrote by Qwen2.5
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotsConfig {

    @JsonProperty("bots")
    private List<YuniBot> bots;

//    public BotsConfig() {
//        bots = new ArrayList<>();
//    }
}
