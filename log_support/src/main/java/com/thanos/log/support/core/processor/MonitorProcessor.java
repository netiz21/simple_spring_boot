package com.thanos.log.support.core.processor;

import com.thanos.log.support.core.condition.MonitorTarget;
import com.thanos.log.support.core.rule.MonitorRule;

/**
 * @author peiheng.zph created on 2017/1/18 22:37
 * @version 1.0
 */
public interface MonitorProcessor<E, T> {

  boolean supports(MonitorRule<E, T> rule);

  T process(MonitorTarget<E> target);
}
