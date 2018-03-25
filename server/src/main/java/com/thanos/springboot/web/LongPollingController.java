package com.thanos.springboot.web;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import com.thanos.springboot.common.ConfigChangeEvent;
import com.thanos.springboot.common.ResponseEnum;
import com.thanos.springboot.common.StandardResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 2018/3/25 10:48
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/polling")
public class LongPollingController {

  private static final int MAX_COUNT = 10000;

  private final AtomicInteger counter = new AtomicInteger(0);

  private ConcurrentHashMap<String, Queue<DeferredResult>> subscribers = new ConcurrentHashMap<>();

  @Autowired
  private EventBus eventBus;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @GetMapping(value = "/configChange", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public DeferredResult<StandardResponse<String>> pollingConfigChange(@RequestParam String configName) {
    DeferredResult<StandardResponse<String>> deferredResult = new DeferredResult();

    if (counter.get() >= MAX_COUNT) {
      deferredResult.setErrorResult(StandardResponse.error(ResponseEnum.REQUEST_EXCEED));
      return deferredResult;
    }

    counter.incrementAndGet();
    subscribers.compute(configName, (key, value) -> {
      Queue<DeferredResult> queue = value == null ? new LinkedList<>() : value;
      queue.add(deferredResult);
      return queue;
    });
    return deferredResult;
  }

  @Subscribe
  public void onConfigChange(ConfigChangeEvent event) {
    BiFunction<String, Queue<DeferredResult>, Queue<DeferredResult>> func = (key, value) -> {
      DeferredResult deferredResult;
      while ((deferredResult = value.poll()) != null) {
        deferredResult.setResult(event.getCurrentValue());
        counter.decrementAndGet();
      }
      return value;
    };
    subscribers.computeIfPresent(event.getConfigName(), func);
  }

}
