package com.thanos.monitor.ext.logback.core.trigger;

/**
 * @author peiheng.zph created on 17/2/13 下午11:19
 * @version 1.0
 */
public class DingDingAlert extends RegularSingleLogMonitorTrigger {

  public DingDingAlert(String alertMsg) {
    super(alertMsg);
  }

  @Override
  public void onSatisfyCondition() {

  }

  @Override
  public void onUnsatisfyCondition() {
    // do nothing
  }
}
