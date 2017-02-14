package com.thanos.monitor.ext.logback.core.condition;

import com.thanos.monitor.ext.logback.base.condition.MonitorCondition;

/**
 * @author peiheng.zph created on 17/2/12 下午5:30
 * @version 1.0
 */
public interface LogMonitorCondition extends MonitorCondition {

  boolean meets(Number input);
}
