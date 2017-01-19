package com.thanos.log.support.core.expression.timeunit;

import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 2017/1/19 0:45
 * @version 1.0
 */
public enum SupportedTimeUnit {

  MINUTE("m") {
    @Override
    public TimeUnit toTimeUnit() {
      return TimeUnit.MINUTES;
    }
  },
  HOUR("h") {
    @Override
    public TimeUnit toTimeUnit() {
      return TimeUnit.HOURS;
    }
  },
  DAY("d") {
    @Override
    public TimeUnit toTimeUnit() {
      return TimeUnit.DAYS;
    }
  };

  SupportedTimeUnit(String unit) {
    this.timeUnit = unit;
  }

  private String timeUnit;

  public String getTimeUnit() {
    return timeUnit;
  }

  public abstract TimeUnit toTimeUnit();

  public static SupportedTimeUnit of(String timeUnit) {
    for (SupportedTimeUnit unit : SupportedTimeUnit.values()) {
      if (unit.getTimeUnit().equals(timeUnit)) {
        return unit;
      }
    }
    return null;
  }
}
