package com.thanos.monitor.ext.logback.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author peiheng.zph created on 17/2/14 上午10:38
 * @version 1.0
 */
public final class Throwables {

  private Throwables() {
  }

  public static String getStackTraceAsString(Throwable throwable) {
    StringWriter stringWriter = new StringWriter();
    throwable.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }
}
