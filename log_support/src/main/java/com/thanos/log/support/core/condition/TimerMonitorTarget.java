package com.thanos.log.support.core.condition;

import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/1/19 上午10:29
 * @version 1.0
 */
public interface TimerMonitorTarget extends MonitorTarget<String> {

  int timeCount();

  TimeUnit timeUnit();
}
