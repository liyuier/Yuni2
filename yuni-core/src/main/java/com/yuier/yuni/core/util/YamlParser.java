package com.yuier.yuni.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Title: YamlParser
 * @Author yuier
 * @Package com.yuier.yuni.common.utils
 * @Date 2024/11/17 1:59
 * @description: yml 文件解析器
 */

public class YamlParser {

    public static <T> T parse(String yamlFile, Class<T> configClass) {
        T configIns = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(yamlFile);
        try {
            configIns = mapper.readValue(file, configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configIns;
    }

    public static <T> T parse(InputStream inputStream, Class<T> configClass) {
        T configIns = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            configIns = mapper.readValue(inputStream, configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configIns;
    }
}
