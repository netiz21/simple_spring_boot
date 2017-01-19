package com.thanos.log.support.core.condition;

import com.thanos.log.support.core.operator.MathOperator;

import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/1/18 下午7:41
 * @version 1.0
 */
public interface TimerMonitorCondittion extends MonitorCondition {

  int timeCount();

  TimeUnit timeUnit();

  MathOperator operator();

  int threshold();
}
