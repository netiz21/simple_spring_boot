package com.thanos.springboot.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author solarknight created on 2016/11/20 10:26
 * @version 1.0
 */
public enum CustomApplicationListener implements ApplicationListener {
  INSTANCE;

  private static final Logger logger = LoggerFactory.getLogger(CustomApplicationListener.class);

  @Override
  public void onApplicationEvent(ApplicationEvent applicationEvent) {
    logger.info("Received event {}", applicationEvent);
  }
}
