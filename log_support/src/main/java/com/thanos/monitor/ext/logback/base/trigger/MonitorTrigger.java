package com.thanos.monitor.ext.logback.base.trigger;

/**
 * @author peiheng.zph created on 17/2/12 下午4:43
 * @version 1.0
 */
public interface MonitorTrigger {

  void onSatisfyCondition();

  void onUnsatisfyCondition();
}
