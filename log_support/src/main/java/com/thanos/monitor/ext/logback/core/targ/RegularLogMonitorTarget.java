package com.thanos.monitor.ext.logback.core.targ;

import com.thanos.monitor.ext.logback.core.event.LogEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import ch.qos.logback.classic.Level;

/**
 * @author peiheng.zph created on 17/2/12 下午6:36
 * @version 1.0
 */
public class RegularLogMonitorTarget implements LogMonitorTarget {
  private String pattern;
  private Level level;
  private int timeCount;
  private TimeUnit timeUnit;
  private boolean ignoreOrigin;

  public RegularLogMonitorTarget() {
  }

  public RegularLogMonitorTarget(String pattern, Level level, int timeCount, TimeUnit timeUnit,
                                 boolean ignoreOrigin) {
    this.pattern = pattern;
    this.level = level;
    this.timeCount = timeCount;
    this.timeUnit = timeUnit;
    this.ignoreOrigin = ignoreOrigin;
  }

  @Override
  public String pattern() {
    return pattern;
  }

  @Override
  public Level level() {
    return level;
  }

  @Override
  public int timeCount() {
    return timeCount;
  }

  @Override
  public TimeUnit timeUnit() {
    return timeUnit;
  }

  @Override
  public boolean match(LogEvent event) {
    // TODO: 17/2/13
    Pattern p = Pattern.compile(pattern);
    event.throwable();
    return false;
  }

  @Override
  public boolean ignoreOrigin() {
    return ignoreOrigin;
  }

  public static LogMonitorTarget of(String targetPattern, String targetLevel, int timeCount,
                                    TimeUnit timeUnit, boolean ignoreOrigin) {
    Level level = Level.toLevel(targetLevel, Level.ALL);
    return new RegularLogMonitorTarget(targetPattern, level, timeCount, timeUnit, ignoreOrigin);
  }
}
