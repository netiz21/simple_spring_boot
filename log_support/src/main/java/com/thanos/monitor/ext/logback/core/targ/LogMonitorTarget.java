package com.thanos.monitor.ext.logback.core.targ;

import com.thanos.monitor.ext.logback.base.targ.MonitorTarget;
import com.thanos.monitor.ext.logback.core.event.LogEvent;

import java.util.concurrent.TimeUnit;

import ch.qos.logback.classic.Level;

/**
 * @author peiheng.zph created on 17/2/12 下午5:24
 * @version 1.0
 */
public interface LogMonitorTarget extends MonitorTarget {

  String pattern();

  Level level();

  int timeCount();

  TimeUnit timeUnit();

  boolean match(LogEvent event);

  boolean ignoreOrigin();
}
