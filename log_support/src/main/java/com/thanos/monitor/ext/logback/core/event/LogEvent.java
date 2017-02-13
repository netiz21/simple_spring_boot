package com.thanos.monitor.ext.logback.core.event;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author peiheng.zph created on 17/2/12 下午10:27
 * @version 1.0
 */
public interface LogEvent {

  Logger logger();

  Level level();

  String format();

  Object[] params();

  Throwable throwable();

}
