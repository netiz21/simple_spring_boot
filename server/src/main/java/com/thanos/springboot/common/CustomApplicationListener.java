package com.thanos.springboot.common;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 2016/11/20 10:26
 * @version 1.0
 */
@Slf4j
public enum CustomApplicationListener implements ApplicationListener {
  INSTANCE;

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    log.info("Received event {}", applicationEvent);
  }
}
