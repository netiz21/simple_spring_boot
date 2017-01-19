package com.thanos.log.support.core.rule;


import com.thanos.log.support.core.condition.MonitorCondition;
import com.thanos.log.support.core.condition.MonitorTarget;

/**
 * @author peiheng.zph created on 2017/1/18 21:17
 * @version 1.0
 */
public interface MonitorRule<E, T> {

  MonitorTarget<E> target();

  MonitorCondition<T> condition();
}
