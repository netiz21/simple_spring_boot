package com.thanos.springboot.common.demo.combinator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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

  @SafeVarargs
  public static <T> Predicate<T> and(Predicate<T>... predicates) {
    Objects.requireNonNull(predicates);
    return Arrays.stream(predicates)
        .reduce(Combinators::alwaysTrue, Predicate::and);
  }

  @SafeVarargs
  public static <T> Predicate<T> or(Predicate<T>... predicates) {
    Objects.requireNonNull(predicates);
    return Arrays.stream(predicates)
        .reduce(Combinators::alwaysFalse, Predicate::or);
  }

  private static <T> boolean alwaysTrue(T t) {
    return true;
  }

  private static <T> boolean alwaysFalse(T t) {
    return false;
  }

}
