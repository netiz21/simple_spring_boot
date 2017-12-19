package com.thanos.springboot.common.demo.combinator;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author peiheng.zph created on 17/12/18 下午5:32
 * @version 1.0
 */
@FunctionalInterface
public interface Before<T, R> extends Function<Consumer<T>, Function<Function<T, R>, Function<T, R>>> {

  static <T, R> Before<T, R> create() {
    return before -> function -> argument -> {
      before.accept(argument);
      return function.apply(argument);
    };
  }

  static <T, R> Function<T, R> decorate(Consumer<T> before, Function<T, R> function) {
    return Before.<T, R>create().apply(before).apply(function);
  }

  public static void main(String[] args) {
    Function<String, Integer> countLen = String::length;
    Function<String, Integer> printAndCountLen = Before.decorate(System.out::println, countLen);

    Stream.of("Moon", "Lilith", "Solar")
        .map(printAndCountLen)
        .collect(Collectors.toList());
  }
}
