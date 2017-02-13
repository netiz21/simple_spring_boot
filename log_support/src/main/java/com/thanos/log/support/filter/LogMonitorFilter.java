package com.thanos.log.support.filter;

import com.thanos.log.support.core.processor.MonitorProcessor;
import com.thanos.log.support.core.processor.TimerMonitorProcessor;
import com.thanos.log.support.core.rule.MonitorRule;
import com.thanos.log.support.core.rule.TimerMonitorRule;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author peiheng.zph created on 2017/1/18 21:15
 * @version 1.0
 */
public class LogMonitorFilter extends BaseMonitorFilter<String, Double> {
  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogMonitorFilter.class);

  @Override
  protected List<TimerMonitorRule> parseMonitorRules(String monitorRuleStr) {
    return TimerMonitorRule.TimeMonitorRuleParser.parse(monitorRuleStr);
  }

  @Override
  protected List<? extends MonitorProcessor<String, Double>> initProcessors() {
    TimerMonitorProcessor processor = new TimerMonitorProcessor();
    List<TimerMonitorProcessor> list = new ArrayList<TimerMonitorProcessor>();
    list.add(processor);
    return list;
  }

  @Override
  protected boolean isLevelMatch(Level level) {
    return level == Level.ERROR;
  }

  @Override
  protected FilterReply onNormalProcess(Marker marker, Logger logger, Level level, String format, Object[] params) {
    return FilterReply.NEUTRAL;
  }

  @Override
  protected FilterReply onErrorProcess(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
    if (t == null) {
      return FilterReply.NEUTRAL;
    }

    String errorMsg = t.getClass().getName() + " " + t.getMessage();
    for (MonitorRule<String, Double> monitorRule : monitorRules) {
      String keyword = monitorRule.target().value();
      if (!errorMsg.contains(keyword)) {
        continue;
      }
      return replyOnHitKeyword(monitorRule, logger);
    }
    return super.onErrorProcess(marker, logger, level, format, params, t);
  }

  private FilterReply replyOnHitKeyword(MonitorRule<String, Double> monitorRule, Logger logger) {
    if (process(monitorRule)) {
      logger.warn("Exception threshold meets monitor rule, rule = {}", monitorRule);
      return FilterReply.NEUTRAL;
    }
    return FilterReply.DENY;
  }

}
