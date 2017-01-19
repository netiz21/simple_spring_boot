package com.thanos.log.support.core.expression;


import com.thanos.log.support.core.expression.operator.MathOperator;

import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 2017/1/19 0:41
 * @version 1.0
 */
public class ThresholdExpression {

  private int timeCount;
  private TimeUnit timeUnit;
  private MathOperator mathOperator;
  private double threshold;

  public int getTimeCount() {
    return timeCount;
  }

  public void setTimeCount(int timeCount) {
    this.timeCount = timeCount;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  public MathOperator getMathOperator() {
    return mathOperator;
  }

  public void setMathOperator(MathOperator mathOperator) {
    this.mathOperator = mathOperator;
  }

  public double getThreshold() {
    return threshold;
  }

  public void setThreshold(double threshold) {
    this.threshold = threshold;
  }
}
