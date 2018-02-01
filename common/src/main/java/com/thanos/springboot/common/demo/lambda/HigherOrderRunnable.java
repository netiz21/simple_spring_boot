package com.thanos.springboot.common.demo.lambda;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author solarknight created on 2018/1/14 13:00
 * @version 1.0
 */
@FunctionalInterface
public interface HigherOrderRunnable {

  Runnable combine(Runnable... runnables);

  static HigherOrderRunnable sequential() {
    return runnables -> () -> Arrays.stream(runnables).forEach(Runnable::run);
  }

  static HigherOrderRunnable parallel(ExecutorService executor) {
    return runnables -> () -> {
      try {
        CountDownLatch latch = new CountDownLatch(runnables.length);
        Arrays.stream(runnables).forEach(task -> executor.submit(() -> {
          try {
            task.run();
          } finally {
            latch.countDown();
          }
        }));
        latch.await();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    };
  }
}
