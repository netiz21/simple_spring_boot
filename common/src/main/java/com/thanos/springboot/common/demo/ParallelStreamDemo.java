package com.thanos.springboot.common.demo;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author solarknight created on 2018/2/4 19:30
 * @version 1.0
 */
public class ParallelStreamDemo {

  public static void main(String[] args) {
    IntUnaryOperator operator = ParallelStreamDemo::transform;

    System.out.println("Available processors " + Runtime.getRuntime().availableProcessors());

    streamDemo(operator);
    parallelStreamDemo(operator);
    completableFutureDemo(operator::applyAsInt);
  }

  public static void streamDemo(IntUnaryOperator operator) {
    long begin = System.currentTimeMillis();
    IntStream.range(0, 100)
        .map(operator)
        .forEach(ParallelStreamDemo::consume);

    System.out.println("Stream demo time cost " + (System.currentTimeMillis() - begin));
  }

  public static void parallelStreamDemo(IntUnaryOperator operator) {
    long begin = System.currentTimeMillis();
    IntStream.range(0, 100)
        .parallel()
        .map(operator)
        .forEach(ParallelStreamDemo::consume);

    System.out.println("Parallel stream demo time cost " + (System.currentTimeMillis() - begin));
  }

  public static void completableFutureDemo(Function<Integer, Integer> function) {
    long begin = System.currentTimeMillis();
    List<CompletableFuture> futures = IntStream.range(0, 100)
        .boxed()
        .map(it -> CompletableFuture.completedFuture(it).thenApplyAsync(function))
        .collect(Collectors.toList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
        .whenComplete((v, th) -> futures.forEach(it -> ParallelStreamDemo.consume((Integer) it.getNow(null))))
        .join();
    System.out.println("Completable future demo time cost " + (System.currentTimeMillis() - begin));
  }

  public static int transform(int i) {
    Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
    return i;
  }

  private static void consume(int i) {
  }
}
