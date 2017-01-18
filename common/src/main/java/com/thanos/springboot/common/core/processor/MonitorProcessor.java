package com.thanos.springboot.common.core.processor;

import com.thanos.springboot.common.core.rule.MonitorRule;
import com.thanos.springboot.common.core.target.MonitorTarget;

/**
 * @author peiheng.zph created on 2017/1/18 22:37
 * @version 1.0
 */
public interface MonitorProcessor<E, T> {

    boolean supports(MonitorRule<E, T> rule);

    T process(MonitorTarget<E> target);
}
