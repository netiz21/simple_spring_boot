package com.thanos.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author peiheng.zph created on 18/1/10 上午10:29
 * @version 1.0
 */
@Configuration
public class SpringSchedulerConfig implements SchedulingConfigurer {

  private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

    threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
    threadPoolTaskScheduler.setThreadNamePrefix("schedule-thread-");
    threadPoolTaskScheduler.initialize();

    scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
  }
}
