package com.thanos.monitor.ext.logback.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author peiheng.zph created on 2017/1/18 23:10
 * @version 1.0
 */
public final class CollectionUtils {

  private CollectionUtils() {
  }

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return map == null || map.isEmpty();
  }

  public static <T> List<T> emptyList() {
    return Collections.emptyList();
  }

  public static <K, V> Map<K, V> emptyMap() {
    return Collections.emptyMap();
  }

  public static <T> List<T> unmodifiableList(List<? extends T> list) {
    return Collections.unmodifiableList(list);
  }
}
