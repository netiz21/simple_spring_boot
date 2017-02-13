package com.thanos.monitor.ext.logback.filter;

import com.thanos.monitor.ext.logback.core.event.LogEvent;
import com.thanos.monitor.ext.logback.core.event.LogEventFactory;
import com.thanos.monitor.ext.logback.core.processor.LogMonitorProcessor;
import com.thanos.monitor.ext.logback.core.processor.RegularLogMonitorProcessor;
import com.thanos.monitor.ext.logback.core.rule.LogMonitorRule;
import com.thanos.monitor.ext.logback.support.LogMonitorRuleParser;
import com.thanos.monitor.ext.logback.util.CollectionUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author peiheng.zph created on 17/2/12 下午4:28
 * @version 1.0
 */
public class LogMonitorFilter extends TurboFilter {
  private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LogMonitorFilter.class);

  private String monitorRuleStr;
  private String monitorContactStr;

  private List<LogMonitorRule> monitorRules;
  private List<LogMonitorProcessor> monitorProcessors;

  @Override
  public void start() {
    super.start();
    initialize();
  }

  protected void initialize() {
    initMonitorRules();
    initMonitorProcessors();
  }

  protected void initMonitorRules() {
    this.monitorRules = LogMonitorRuleParser.parse(monitorRuleStr);
    if (CollectionUtils.isEmpty(monitorRules)) {
      LOG.error("Parse no rules from rule str = {}", monitorRuleStr);
      throw new IllegalArgumentException("Error parse monitor rules!");
    }
  }

  protected void initMonitorProcessors() {
    this.monitorProcessors = new ArrayList<LogMonitorProcessor>();
    RegularLogMonitorProcessor processor = new RegularLogMonitorProcessor();
    monitorProcessors.add(processor);
  }

  @Override
  public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
    LogEvent logEvent = LogEventFactory.newEvent(logger, level, format, params, t);

    FilterReply reply = FilterReply.NEUTRAL;
    for (LogMonitorRule rule : monitorRules) {
      if (!rule.target().match(logEvent)) {
        continue;
      }

      onHitRule(rule);
      if (rule.ignoreOrigin()) {
        reply = FilterReply.DENY;
      }
    }

    return reply;
  }

  private void onHitRule(LogMonitorRule rule) {
    for (LogMonitorProcessor processor : monitorProcessors) {
      if (processor.supports(rule)) {
        processor.process(rule);
      }
    }
  }

  public void setMonitorRuleStr(String monitorRuleStr) {
    this.monitorRuleStr = monitorRuleStr;
  }

  public void setMonitorContactStr(String monitorContactStr) {
    this.monitorContactStr = monitorContactStr;
  }
}
