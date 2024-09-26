package org.zishi.mq.websocketserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zishi
 */
public class JacksonUtil<T> {
    private static final Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    public static String writeValueAsString(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {

            //throw new RuntimeException("Json序列化失败....");
            logger.error("Json序列化失败....", e);
            return "";
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return MAPPER.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            logger.error("Json反序列化失败...., {}", content.substring(0, Math.min(content.length(), 50)), e);
            throw new RuntimeException("Json反序列化失败....");
            //return null;
        }
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return MAPPER.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {

            logger.error("Json反序列化失败, 内容为： {}", content.substring(0, Math.min(content.length(), 50)), e);
            throw new RuntimeException("Json反序列化失败, 内容为： ");
            //return null;
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) throws IllegalArgumentException {
        return MAPPER.convertValue(fromValue, toValueType);
    }
}
