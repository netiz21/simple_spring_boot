package com.thanos.monitor.ext.logback.base.processor;

import com.thanos.monitor.ext.logback.base.rule.MonitorRule;

/**
 * @author peiheng.zph created on 17/2/12 下午4:33
 * @version 1.0
 */
public interface MonitorProcessor {

  boolean supports(MonitorRule rule);

  void process(MonitorRule rule);
}
