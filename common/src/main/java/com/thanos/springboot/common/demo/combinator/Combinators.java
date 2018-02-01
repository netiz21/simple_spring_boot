package com.thanos.springboot.common.demo.combinator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author peiheng.zph created on 18/2/1 下午5:01
 * @version 1.0
 */
public final class Combinators {

  static <T, R> Function<T, R> combine(Consumer<T> before, Function<T, R> function) {
    return Before.decorate(before, function);
  }

  static <T, R> Function<T, R> combine(Function<T, R> function, BiConsumer<T, R> after) {
    return After.decorate(function, after);
  }

  static <T> Consumer<T> combine(Runnable before, Consumer<T> consumer) {
    return it -> {
      before.run();
      consumer.accept(it);
    };
  }

  static <T> Consumer<T> combine(Consumer<T> consumer, Runnable after) {
    return it -> {
      consumer.accept(it);
      after.run();
    };
  }

  static <T> Consumer<T> combine(Consumer<T> before, Consumer<T> after) {
    return it -> {
      before.accept(it);
      after.accept(it);
    };
  }

  static <T, R> Combinator<T, R> target(Function<T, R> function) {
    return function::apply;
  }

}
