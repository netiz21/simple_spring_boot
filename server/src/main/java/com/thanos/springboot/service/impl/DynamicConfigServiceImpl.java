package com.thanos.springboot.service.impl;

import com.google.common.eventbus.EventBus;

import com.thanos.springboot.common.ConfigChangeEvent;
import com.thanos.springboot.service.DynamicConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 2018/3/25 19:14
 * @version 1.0
 */
@Slf4j
@Service
public class DynamicConfigServiceImpl implements DynamicConfigService {

  @Autowired
  private EventBus eventBus;

  private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  private Map<String, Object> configs = new HashMap<>();

  @PostConstruct
  public void init() {
    configs.put("conf0", "ooo");
    configs.put("conf1", "aaa");
    configs.put("conf2", "bbb");
    configs.put("conf3", "ccc");

    mockConfigChange();
  }

  private void mockConfigChange() {
    Runnable changeConfig = () -> {
      Random random = new Random();
      String conf = "conf" + random.nextInt(4);
      String newValue = EnhancedRandom.random(String.class);
      String originValue = (String) configs.replace(conf, newValue);

      ConfigChangeEvent event = new ConfigChangeEvent();
      event.setConfigName(conf);
      event.setCurrentValue(newValue);
      event.setOriginValue(originValue);
      eventBus.post(event);

      log.info("Config {} changed from {} to {}", conf, originValue, newValue);
    };

    scheduler.scheduleWithFixedDelay(changeConfig, 1, 2, TimeUnit.SECONDS);
  }

  @Override
  public <T> T getOrDefault(String key, T defaultValue) {
    Object val = configs.get(key);
    return val == null ? defaultValue : (T) val;
  }
}
