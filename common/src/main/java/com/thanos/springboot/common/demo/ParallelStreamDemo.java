package com.thanos.springboot.common.demo;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * @author solarknight created on 2018/2/4 19:30
 * @version 1.0
 */
public class ParallelStreamDemo {

  public static void main(String[] args) {
    IntUnaryOperator operator = ParallelStreamDemo::transform;
    parallelStreamDemo(operator);
    streamDemo(operator);
  }

  public static void streamDemo(IntUnaryOperator operator) {
    long begin = System.currentTimeMillis();
    IntStream.range(0, Integer.MAX_VALUE)
        .parallel()
        .map(operator)
        .forEach(ParallelStreamDemo::consume);

    System.out.println("Time cost " + (System.currentTimeMillis() - begin));
  }

  public static void parallelStreamDemo(IntUnaryOperator operator) {
    long begin = System.currentTimeMillis();
    IntStream.range(0, Integer.MAX_VALUE)
        .map(operator)
        .forEach(ParallelStreamDemo::consume);

    System.out.println("Time cost " + (System.currentTimeMillis() - begin));
  }

  public static int transform(int i) {
    return (int) ((i + i % 37 + Math.PI * i) / 21);
  }

  private static void consume(int i) {
  }
}
