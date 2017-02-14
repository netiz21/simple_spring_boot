package com.thanos.monitor.ext.logback.core.processor;

import com.thanos.monitor.ext.logback.base.rule.MonitorRule;
import com.thanos.monitor.ext.logback.core.rule.LogMonitorRule;
import com.thanos.monitor.ext.logback.core.targ.LogMonitorTarget;
import com.thanos.monitor.ext.logback.util.ExpireList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peiheng.zph created on 17/2/12 下午9:48
 * @version 1.0
 */
public class RegularLogMonitorProcessor implements LogMonitorProcessor {

  private Map<String, ExpireList<Object>> monitorMap = new HashMap<String, ExpireList<Object>>();

  @Override
  public boolean supports(MonitorRule rule) {
    return rule != null && rule instanceof LogMonitorRule;
  }

  @Override
  public void process(MonitorRule rule) {
    LogMonitorRule monitorRule = (LogMonitorRule) rule;

    Double count = calCount(monitorRule.target());

    if (monitorRule.condition().meets(count)) {
      monitorRule.trigger().onSatisfyCondition();
    } else {
      monitorRule.trigger().onUnsatisfyCondition();
    }
  }

  private Double calCount(LogMonitorTarget target) {
    // contains, get list and add
    if (monitorMap.containsKey(target.pattern())) {
      ExpireList<Object> list = monitorMap.get(target.pattern());
      list.add(new Object());
      return (double) list.size();
    }
    // not contains, add new expire list
    else {
      ExpireList<Object> list = new ExpireList<Object>(target.timeCount(),
          target.timeUnit());
      list.add(new Object());
      monitorMap.put(target.pattern(), list);
      return (double) list.size();
    }
  }

}
