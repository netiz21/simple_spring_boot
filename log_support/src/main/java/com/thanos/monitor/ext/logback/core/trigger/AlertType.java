package com.thanos.monitor.ext.logback.core.trigger;

/**
 * @author peiheng.zph created on 17/2/13 下午7:10
 * @version 1.0
 */
public enum AlertType {
  LOG("log"), DING("ding"), SMS("sms");

  AlertType(String type) {
    this.type = type;
  }

  private String type;

  public String getType() {
    return type;
  }

  public static AlertType of(String type) {
    for (AlertType alertType : AlertType.values()) {
      if (alertType.getType().equalsIgnoreCase(type)) {
        return alertType;
      }
    }
    return null;
  }
}
