package com.thanos.monitor.ext.logback.core.rule;

import com.thanos.monitor.ext.logback.base.rule.MonitorRule;
import com.thanos.monitor.ext.logback.core.condition.LogMonitorCondition;
import com.thanos.monitor.ext.logback.core.targ.LogMonitorTarget;
import com.thanos.monitor.ext.logback.core.trigger.LogMonitorTrigger;

/**
 * @author peiheng.zph created on 17/2/12 下午5:29
 * @version 1.0
 */
public interface LogMonitorRule extends MonitorRule {

  LogMonitorTarget target();

  LogMonitorCondition condition();

  LogMonitorTrigger trigger();
}
