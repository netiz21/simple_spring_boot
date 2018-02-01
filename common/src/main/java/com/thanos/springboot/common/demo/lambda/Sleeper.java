package com.thanos.springboot.common.demo.lambda;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author solarknight created on 2018/1/14 13:01
 * @version 1.0
 */
public enum Sleeper {

  TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7);

  private final int seconds;

  Sleeper(int seconds) {
    this.seconds = seconds;
  }

  /**
   * Sleeps the number of seconds specified by this enum's
   * seconds attribute, generating a countdown output.
   */
  public void sleep() {
    System.out.printf("%s => Sleeping %d seconds...%n", this, seconds);
    // Countdown iteration: n, n - 1, ..., 0
    IntStream.iterate(seconds, i -> i - 1).limit(seconds).forEach(i -> {
      try {
        System.out.printf("%s => %d%n", this, i);
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        // Propagate interrupt status
        Thread.currentThread().interrupt();
        System.out.printf("%s => Interrupted!%n", this);
      }
    });
    System.out.printf("%s => 0. Waking up...%n", this);
  }

  public static void main(String[] args) {
    // Create an executor capable of running 6 tasks in parallel
    ExecutorService executor = Executors.newFixedThreadPool(6);

    // Create a parallel higher-order runnable, that will combine runnables
    // by making them run in parallel
    HigherOrderRunnable parallel = HigherOrderRunnable.parallel(executor);

    // Use the parallel higher-order runnable to combine all our runnables
    // (Note that the result of combining all our runnables is also a
    // runnable)
    Runnable parallel2_7 = parallel.combine(
        Sleeper.TWO::sleep,
        Sleeper.THREE::sleep,
        Sleeper.FOUR::sleep,
        Sleeper.FIVE::sleep,
        Sleeper.SIX::sleep,
        Sleeper.SEVEN::sleep);

    System.out.println("-----------------------------------------");
    System.out.println("PARALLEL");
    System.out.println("-----------------------------------------");

    long init = System.currentTimeMillis();

    // Run our fresh new runnable
    parallel2_7.run();

    long end = System.currentTimeMillis();

    System.out.println("-----------------------------------------");
    System.out.println("Total elapsed time: " + (end - init) / 1_000 + " seconds");
    System.out.println("-----------------------------------------");

    // Release executor resources
    executor.shutdown();
  }

}
