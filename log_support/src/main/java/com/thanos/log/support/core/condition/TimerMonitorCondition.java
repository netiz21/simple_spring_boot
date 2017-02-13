package com.thanos.log.support.core.condition;

import com.thanos.monitor.ext.logback.core.expression.MathOperator;

/**
 * @author peiheng.zph created on 2017/1/18 21:18
 * @version 1.0
 */
public interface TimerMonitorCondition extends MonitorCondition<Double> {

  MathOperator operator();

  double threshold();
}
