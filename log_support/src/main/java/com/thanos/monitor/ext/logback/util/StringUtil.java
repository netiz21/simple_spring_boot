package com.thanos.monitor.ext.logback.util;

/**
 * @author peiheng.zph created on 17/2/14 上午11:35
 * @version 1.0
 */
public final class StringUtil {

  public static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }
}
