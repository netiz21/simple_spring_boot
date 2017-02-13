package com.thanos.monitor.ext.logback.core.trigger;

/**
 * @author peiheng.zph created on 17/2/13 下午7:13
 * @version 1.0
 */
public abstract class RegularSingleLogMonitorTrigger implements LogMonitorTrigger {

  protected String alertMsg;

  public RegularSingleLogMonitorTrigger(String alertMsg) {
    this.alertMsg = alertMsg;
  }

  public static LogMonitorTrigger of(String alertMsg, String alertType) {
    AlertType type = AlertType.of(alertType);
    if (type == null) {
      throw new IllegalArgumentException("Illegal alert type " + alertType);
    }

    switch (type) {
      case LOG:
        return new LogbackAlert(alertMsg);
      case DING:
        return new DingDingAlert(alertMsg);
      case SMS:
        return new SMSAlert(alertMsg);
    }
    throw new IllegalStateException("Illegal alert type " + alertType);
  }
}
