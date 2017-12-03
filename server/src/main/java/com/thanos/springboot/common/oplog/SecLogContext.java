package com.thanos.springboot.common.oplog;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 17/2/25 下午5:35
 * @version 1.0
 */
@Slf4j
public class SecLogContext {

  private static ThreadLocal<Boolean> logSign = new ThreadLocal<>();

  public static boolean checkLogContext() {
    return Objects.equals(logSign.get(), Boolean.TRUE);
  }

  public static void startLogContext() {
    if (checkLogContext()) {
      throw new IllegalStateException("Log context already started now!");
    }
    log.info("Start log context now");
    logSign.set(Boolean.TRUE);
  }

  public static void destroyLogContext() {
    log.info("Destroy log context");
    logSign.remove();
  }
}
