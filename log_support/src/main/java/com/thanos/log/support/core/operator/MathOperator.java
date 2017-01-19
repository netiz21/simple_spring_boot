package com.thanos.log.support.core.operator;

/**
 * @author peiheng.zph created on 17/1/18 下午7:58
 * @version 1.0
 */
public enum MathOperator {
  LESS("<"), GREATER(">"), NO_LESS(">="), NO_GREATER("<=");

  MathOperator(String operator) {
    this.operator = operator;
  }

  private String operator;

}
