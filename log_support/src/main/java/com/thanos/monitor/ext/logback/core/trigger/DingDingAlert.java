package com.thanos.monitor.ext.logback.core.trigger;

import com.thanos.monitor.ext.logback.support.contact.MonitorContacts;
import com.thanos.monitor.ext.logback.support.notify.DING;

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
    DING.send(alertMsg, MonitorContacts.allContact());
  }

  @Override
  public void onUnsatisfyCondition() {
    // do nothing
  }
}
