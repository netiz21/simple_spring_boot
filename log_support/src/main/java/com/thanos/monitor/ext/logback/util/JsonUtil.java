package com.thanos.monitor.ext.logback.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author peiheng.zph created on 17/2/13 下午4:43
 * @version 1.0
 */
public final class JsonUtil {
  private static final ObjectMapper mapper = new ObjectMapper();

  private JsonUtil() {
  }

  public static <T> T parseObject(String str, Class<T> cls) {
    try {
      return mapper.readValue(str, cls);
    } catch (IOException e) {
      throw new JsonConvertException(e);
    }
  }

  public static <T> List<T> parseArray(String str, Class<T> cls) {
    TypeFactory factory = mapper.getTypeFactory();
    try {
      return mapper.readValue(str, factory.constructCollectionType(List.class, cls));
    } catch (IOException e) {
      throw new JsonConvertException(e);
    }
  }

  public static String toJSON(Object value) {
    String res;
    try {
      res = mapper.writeValueAsString(value);
    } catch (IOException e) {
      throw new JsonConvertException(e);
    }
    return res;
  }

}
