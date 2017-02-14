package com.thanos.monitor.ext.logback.support.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author peiheng.zph created on 17/2/14 下午6:33
 * @version 1.0
 */
public final class TaskService {

  private static final int THREAD_COUNT = 2;

  private static final ExecutorService exec = Executors.newFixedThreadPool(THREAD_COUNT);

  private TaskService() {
  }

  public static void run(final Task task) {
    exec.submit(new Runnable() {
      @Override
      public void run() {
        task.run();
      }
    });
  }

}
