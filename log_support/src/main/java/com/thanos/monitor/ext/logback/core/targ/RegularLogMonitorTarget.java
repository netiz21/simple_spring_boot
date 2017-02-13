package com.thanos.monitor.ext.logback.core.targ;

import com.thanos.monitor.ext.logback.core.event.LogEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
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

  public RegularLogMonitorTarget() {
  }

  public RegularLogMonitorTarget(String pattern, Level level, int timeCount, TimeUnit timeUnit) {
    this.pattern = pattern;
    this.level = level;
    this.timeCount = timeCount;
    this.timeUnit = timeUnit;
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
    Pattern p = Pattern.compile(pattern);
    event.throwable();
    return false;
  }

  public static LogMonitorTarget of(String targetPattern, String targetLevel, int timeCount, TimeUnit timeUnit) {
    Level level = Level.toLevel(targetLevel, Level.ALL);
    return new RegularLogMonitorTarget(targetPattern, level, timeCount, timeUnit);
  }
}
