package com.thanos.monitor.ext.logback.base.rule;

import com.thanos.monitor.ext.logback.base.condition.MonitorCondition;
import com.thanos.monitor.ext.logback.base.trigger.MonitorTrigger;
import com.thanos.monitor.ext.logback.base.targ.MonitorTarget;

/**
 * @author peiheng.zph created on 17/2/12 下午4:15
 * @version 1.0
 */
public interface MonitorRule {

  MonitorTarget target();

  MonitorCondition condition();

  MonitorTrigger trigger();
}
