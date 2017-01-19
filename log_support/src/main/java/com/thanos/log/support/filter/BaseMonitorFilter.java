package com.thanos.log.support.filter;

import com.thanos.log.support.core.processor.MonitorProcessor;
import com.thanos.log.support.core.rule.MonitorRule;
import com.thanos.log.support.util.CollectionUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author peiheng.zph created on 2017/1/18 21:12
 * @version 1.0
 */
public abstract class BaseMonitorFilter<E, T> extends TurboFilter {
  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseMonitorFilter.class);

  private String monitorRuleStr;

  protected List<? extends MonitorRule<E, T>> monitorRules;

  protected List<? extends MonitorProcessor<E, T>> monitorProcessors;

  protected abstract boolean isLevelMatch(Level level);

  protected abstract List<? extends MonitorRule<E, T>> parseMonitorRules(String monitorRuleStr);

  protected abstract List<? extends MonitorProcessor<E, T>> initProcessors();

  @Override
  public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
    if (!isLevelMatch(level)) {
      return FilterReply.NEUTRAL;
    }
    if (level == Level.ERROR || t != null) {
      return onErrorProcess(marker, logger, level, format, params, t);
    }
    return onNormalProcess(marker, logger, level, format, params);
  }

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
    List<? extends MonitorRule<E, T>> rules = parseMonitorRules(monitorRuleStr);
    if (CollectionUtils.isEmpty(rules)) {
      logger.error("Parse no rules from rule str = {}", monitorRuleStr);
      throw new IllegalArgumentException("Error parse monitor rules!");
    }
    this.monitorRules = rules;
  }

  protected void initMonitorProcessors() {
    List<? extends MonitorProcessor<E, T>> processors = initProcessors();
    if (CollectionUtils.isEmpty(processors)) {
      logger.error("No processors defined!");
      throw new IllegalStateException("No processors defined");
    }
    this.monitorProcessors = processors;
  }

  protected FilterReply onNormalProcess(Marker marker, Logger logger, Level level, String format, Object[] params) {
    return FilterReply.NEUTRAL;
  }

  protected FilterReply onErrorProcess(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
    return FilterReply.NEUTRAL;
  }

  protected boolean process(MonitorRule<E, T> rule) {
    for (MonitorProcessor<E, T> processor : monitorProcessors) {
      if (!processor.supports(rule)) {
        continue;
      }
      if (rule.condition().meets(processor.process(rule.target()))) {
        return true;
      }
    }
    return false;
  }

  public void setMonitorRuleStr(String monitorRuleStr) {
    this.monitorRuleStr = monitorRuleStr;
  }
}