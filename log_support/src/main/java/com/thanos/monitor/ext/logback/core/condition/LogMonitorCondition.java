package com.thanos.monitor.ext.logback.core.condition;

import com.thanos.monitor.ext.logback.base.condition.MonitorCondition;
import com.thanos.monitor.ext.logback.core.expression.MathOperator;

/**
 * @author peiheng.zph created on 17/2/12 下午5:30
 * @version 1.0
 */
public interface LogMonitorCondition extends MonitorCondition {

  MathOperator operator();

  double threshold();
}
