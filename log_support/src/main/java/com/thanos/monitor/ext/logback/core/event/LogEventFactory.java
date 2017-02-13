package com.thanos.monitor.ext.logback.core.event;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * @author peiheng.zph created on 17/2/12 下午10:32
 * @version 1.0
 */
public final class LogEventFactory {

  public static LogEvent newEvent(Logger logger, Level level, String format, Object[] params,
                                  Throwable t) {
    return new NormalLogEvent(logger, level, format, params, t);
  }

  private LogEventFactory() {
  }

  private static class NormalLogEvent implements LogEvent {

    private Logger logger;
    private Level level;
    private String format;
    private Object[] params;
    private Throwable t;

    public NormalLogEvent() {
    }

    public NormalLogEvent(Logger logger, Level level, String format, Object[] params, Throwable t) {
      this.logger = logger;
      this.level = level;
      this.format = format;
      this.params = params;
      this.t = t;
    }

    @Override
    public Logger logger() {
      return logger;
    }

    @Override
    public Level level() {
      return level;
    }

    @Override
    public String format() {
      return format;
    }

    @Override
    public Object[] params() {
      return params;
    }

    @Override
    public Throwable throwable() {
      return t;
    }
  }
}
