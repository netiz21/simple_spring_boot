package com.thanos.springboot.common.demo.lambda;

import java.util.concurrent.Callable;

/**
 * @author peiheng.zph created on 17/8/21 下午9:08
 * @version 1.0
 */
public final class SimpleUtil {

  public static <T> T uncheckCall(Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      return sneakyThrow(e);
    }
  }

  private static <E extends Throwable, T> T sneakyThrow0(Throwable t) throws E {
    throw (E) t;
  }

  public static <T> T sneakyThrow(Throwable e) {
    return SimpleUtil.sneakyThrow0(e);
  }
}
