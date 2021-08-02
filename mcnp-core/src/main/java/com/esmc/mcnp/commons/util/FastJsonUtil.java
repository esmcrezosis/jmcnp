package com.esmc.mcnp.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author doublelife
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-25
 * Time: 23:21
 * Description:
 */
public class FastJsonUtil {

    private static final SerializeConfig CONFIG;

    static {
        CONFIG = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 使用和json-lib兼容的日期输出格式
        CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    private static final SerializerFeature[] FEATURES = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteDateUseDateFormat
    };

    public static String toJsonString(Object object) {
        return JSON.toJSONString(object, CONFIG, FEATURES);
    }

    public static String toJsonStringNoFeatures(Object object) {
        return JSON.toJSONString(object, CONFIG);
    }

    public static String toJsonStringNoConfig(Object object) {
        return JSON.toJSONString(object, FEATURES);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

}
