package com.thanos.log.support.filter;

import com.thanos.log.support.core.monitor.Monitor;

import ch.qos.logback.classic.turbo.TurboFilter;

/**
 * @author peiheng.zph created on 17/1/18 下午7:48
 * @version 1.0
 */
public abstract class BaseMonitorFilter extends TurboFilter implements Monitor {

  protected String monitorRuleStr;

  public String getMonitorRuleStr() {
    return monitorRuleStr;
  }

  public void setMonitorRuleStr(String monitorRuleStr) {
    this.monitorRuleStr = monitorRuleStr;
  }
}
