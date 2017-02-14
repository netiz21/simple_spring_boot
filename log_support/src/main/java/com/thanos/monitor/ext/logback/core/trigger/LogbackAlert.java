package com.thanos.monitor.ext.logback.core.trigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peiheng.zph created on 17/2/13 下午11:17
 * @version 1.0
 */
public class LogbackAlert extends RegularSingleLogMonitorTrigger {
  private static final Logger logger = LoggerFactory.getLogger(LogbackAlert.class);

  public LogbackAlert(String alertMsg) {
    super(alertMsg);
  }

  @Override
  public void onSatisfyCondition() {
    logger.error(alertMsg);
  }

  @Override
  public void onUnsatisfyCondition() {
    // do nothing
  }
}
