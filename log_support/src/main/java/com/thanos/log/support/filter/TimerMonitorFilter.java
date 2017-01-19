package com.thanos.log.support.filter;

import org.slf4j.Marker;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author peiheng.zph created on 17/1/18 下午4:53
 * @version 1.0
 */
public class TimerMonitorFilter extends BaseMonitorFilter {


  @Override
  public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
    return FilterReply.DENY;
  }

}
