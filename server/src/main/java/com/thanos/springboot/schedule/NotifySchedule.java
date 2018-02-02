package com.thanos.springboot.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

/**
 * @author peiheng.zph created on 18/2/2 下午5:39
 * @version 1.0
 */
@Slf4j
@Component
public class NotifySchedule {

  @Scheduled(cron = "0 0/1 * * * ?")
  public void hello() {
    log.info("Current time is {}", LocalDateTime.now());
  }

}
