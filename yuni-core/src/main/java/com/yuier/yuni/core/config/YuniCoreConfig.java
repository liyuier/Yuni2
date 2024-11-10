package com.yuier.yuni.core.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

/**
 * @Title: YuniCoreConfig
 * @Author yuier
 * @Package com.yuier.yuni.core.config
 * @Date 2024/11/10 20:42
 * @description: 配置类
 */

@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@ComponentScan("com.yuier.yuni.common")
public class YuniCoreConfig {

    /**
     * 用于将接收到的请求中的数字类型均转为 Long 类型
     * @return 一个 lambda
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.deserializerByType(Number.class, new NumberToLongDeserializer());
        };
    }

    private static class NumberToLongDeserializer extends JsonDeserializer<Number> {
        @Override
        public Number deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            if (parser.getCurrentToken().isNumeric()) {
                // Assuming all numbers should be deserialized as long
                return parser.getLongValue();
            }
            // Fallback to default deserializer if not a number
            return (Number) context.handleUnexpectedToken(Number.class, parser);
        }
    }
}
