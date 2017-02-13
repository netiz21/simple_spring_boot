package com.thanos.monitor.ext.logback.core.trigger;

import com.thanos.monitor.ext.logback.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiheng.zph created on 17/2/12 下午6:34
 * @version 1.0
 */
public class RegularMultiLogMonitorTrigger implements LogMonitorTrigger {

  private List<LogMonitorTrigger> triggers;

  public RegularMultiLogMonitorTrigger() {
  }

  public RegularMultiLogMonitorTrigger(List<LogMonitorTrigger> triggers) {
    this.triggers = triggers;
  }

  @Override
  public void onSatisfyCondition() {
    for (LogMonitorTrigger trigger : triggers) {
      trigger.onSatisfyCondition();
    }
  }

  @Override
  public void onUnsatisfyCondition() {
    for (LogMonitorTrigger trigger : triggers) {
      trigger.onUnsatisfyCondition();
    }
  }

  public static LogMonitorTrigger of(String alertMsg, List<String> alertTypes) {
    List<LogMonitorTrigger> triggers = new ArrayList<LogMonitorTrigger>();
    if (CollectionUtils.isEmpty(alertTypes)) {
      return new RegularMultiLogMonitorTrigger(triggers);
    }

    for (String alertType : alertTypes) {
      triggers.add(RegularSingleLogMonitorTrigger.of(alertMsg, alertType));
    }
    return new RegularMultiLogMonitorTrigger(triggers);
  }
}
