package com.thanos.log.support.core.processor;

import com.thanos.log.support.core.condition.MonitorTarget;
import com.thanos.log.support.core.condition.TimerMonitorTarget;
import com.thanos.log.support.core.rule.MonitorRule;
import com.thanos.log.support.core.rule.TimerMonitorRule;
import com.thanos.log.support.util.ExpireList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peiheng.zph created on 17/1/19 上午11:01
 * @version 1.0
 */
public class TimerMonitorProcessor implements MonitorProcessor<String, Double> {

  private Map<String, ExpireList<Object>> monitorMap = new HashMap<String, ExpireList<Object>>();

  @Override
  public boolean supports(MonitorRule<String, Double> rule) {
    return rule != null && rule instanceof TimerMonitorRule;
  }

  @Override
  public Double process(MonitorTarget<String> target) {
    TimerMonitorTarget monitorTarget = (TimerMonitorTarget) target;
    // contains, get list and add
    if (monitorMap.containsKey(monitorTarget.value())) {
      ExpireList<Object> list = monitorMap.get(monitorTarget.value());
      list.add(new Object());
      return (double) list.size();
    }
    // not contains, add new expire list
    else {
      ExpireList<Object> list = new ExpireList<Object>(monitorTarget.timeCount(),
          monitorTarget.timeUnit());
      list.add(new Object());
      monitorMap.put(target.value(), list);
      return (double) list.size();
    }
  }

}
