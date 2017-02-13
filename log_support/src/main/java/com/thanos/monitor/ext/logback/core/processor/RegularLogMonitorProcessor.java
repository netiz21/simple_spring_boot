package com.thanos.monitor.ext.logback.core.processor;

import com.thanos.monitor.ext.logback.base.rule.MonitorRule;
import com.thanos.monitor.ext.logback.core.rule.LogMonitorRule;

/**
 * @author peiheng.zph created on 17/2/12 下午9:48
 * @version 1.0
 */
public class RegularLogMonitorProcessor implements LogMonitorProcessor{

  @Override
  public boolean supports(MonitorRule rule) {
    return rule != null && rule instanceof LogMonitorRule;
  }

  @Override
  public void process(MonitorRule rule) {

  }
}
