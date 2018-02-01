package com.thanos.springboot.common.demo.combinator;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author solarknight created on 2018/1/7 17:45
 * @version 1.0
 */
@FunctionalInterface
public interface Around<T, R> extends Function<Function<T, R>, Function<BiConsumer<Around.Executable<R>, T>, Function<T, R>>> {

  @FunctionalInterface
  interface Executable<R> {
    R execute();
  }

  static <T, R> Around<T, R> create() {
    return function -> around -> argument -> {
      @SuppressWarnings("unchecked")
      R[] result = (R[]) new Object[1];
      Executable<R> callback = () -> result[0] = function.apply(argument);
      around.accept(callback, argument);
      return result[0];
    };
  }

  static <T, R> Function<T, R> decorate(
      Function<T, R> function,
      BiConsumer<Executable<R>, T> around) {
    return Around.<T, R>create().apply(function).apply(around);
  }
}
