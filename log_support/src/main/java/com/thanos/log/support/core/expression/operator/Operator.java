package com.thanos.log.support.core.expression.operator;

/**
 * @author peiheng.zph created on 2017/1/18 21:23
 * @version 1.0
 */
public interface Operator<E, T> {
  boolean matches(E input, T target);
}
