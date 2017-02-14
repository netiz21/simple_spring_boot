package com.thanos.monitor.ext.logback.core.targ;

import com.thanos.monitor.ext.logback.core.event.LogEvent;
import com.thanos.monitor.ext.logback.util.LogFormatUtil;
import com.thanos.monitor.ext.logback.util.Throwables;

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
    return isLevelMatch(event) && isPatternMatch(event);
  }

  private boolean isLevelMatch(LogEvent event) {
    Level targetLevel = event.level();
    return this.level == Level.ALL || this.level == targetLevel;
  }

  private boolean isPatternMatch(LogEvent event) {
    Pattern p = Pattern.compile(pattern);
    return isLogMessageMatch(p, event) || isExTraceMatch(p, event);
  }

  private boolean isLogMessageMatch(Pattern pattern, LogEvent event) {
    String msg = LogFormatUtil.doFormat(event.format(), event.params());
    Matcher m = pattern.matcher(msg);
    return m.find();
  }

  private boolean isExTraceMatch(Pattern pattern, LogEvent event) {
    if (event.throwable() == null) {
      return false;
    }

    String stackTrace = Throwables.getStackTraceAsString(event.throwable());
    Matcher m = pattern.matcher(stackTrace);
    return m.find();
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
