package com.thanos.monitor.ext.logback.core.condition;

import com.thanos.monitor.ext.logback.core.expression.MathOperator;

/**
 * @author peiheng.zph created on 17/2/12 下午6:35
 * @version 1.0
 */
public class RegularLogMonitorCondition implements LogMonitorCondition {
  private MathOperator operator;
  private double threshold;

  public RegularLogMonitorCondition() {
  }

  public RegularLogMonitorCondition(MathOperator operator, double threshold) {
    this.operator = operator;
    this.threshold = threshold;
  }

  public static LogMonitorCondition of(MathOperator mathOperator, double threshold) {
    return new RegularLogMonitorCondition(mathOperator, threshold);
  }

  @Override
  public boolean meets(Number input) {
    return operator.matches(input.doubleValue(), threshold);
  }
}
