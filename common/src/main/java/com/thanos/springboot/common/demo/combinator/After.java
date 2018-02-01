package com.thanos.springboot.common.demo.combinator;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author solarknight created on 2018/1/7 17:45
 * @version 1.0
 */
@FunctionalInterface
public interface After<T, R> extends Function<Function<T, R>, Function<BiConsumer<T, R>, Function<T, R>>> {

  static <T, R> After<T, R> create() {
    return function -> after -> argument -> {
      R result = function.apply(argument);
      after.accept(argument, result);
      return result;
    };
  }

  static <T, R> Function<T, R> decorate(
      Function<T, R> function,
      BiConsumer<T, R> after) {
    return After.<T, R>create().apply(function).apply(after);
  }
}
