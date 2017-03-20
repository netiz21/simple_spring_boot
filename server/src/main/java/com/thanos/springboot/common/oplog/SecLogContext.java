package com.thanos.springboot.common.oplog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author solarknight created on 17/2/25 下午5:35
 * @version 1.0
 */
public class SecLogContext {
  private static final Logger logger = LoggerFactory.getLogger(SecLogContext.class);

  private static ThreadLocal<Boolean> logSign = new ThreadLocal<>();

  public static boolean checkLogContext() {
    return Objects.equals(logSign.get(), Boolean.TRUE);
  }

  public static void startLogContext() {
    if (checkLogContext()) {
      throw new IllegalStateException("Log context already started now!");
    }
    logger.info("Start log context now");
    logSign.set(Boolean.TRUE);
  }

  public static void destroyLogContext() {
    logger.info("Destroy log context");
    logSign.remove();
  }
}
