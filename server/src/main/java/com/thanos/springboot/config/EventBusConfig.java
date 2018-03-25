package com.thanos.springboot.config;

import com.google.common.eventbus.EventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author solarknight created on 2018/3/25 19:08
 * @version 1.0
 */
@Configuration
public class EventBusConfig {

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }
}
