package com.thanos.springboot.common.core.rule;

import com.thanos.springboot.common.core.condition.MonitorCondition;
import com.thanos.springboot.common.core.target.MonitorTarget;

/**
 * @author peiheng.zph created on 2017/1/18 21:17
 * @version 1.0
 */
public interface MonitorRule<E, T> {

    MonitorTarget<E> target();

    MonitorCondition<T> condition();
}
