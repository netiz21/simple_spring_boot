package com.thanos.log.support.core.condition;

/**
 * @author peiheng.zph created on 2017/1/18 21:17
 * @version 1.0
 */
public interface MonitorCondition<E> {
  boolean meets(E input);
}
