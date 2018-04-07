package com.thanos.springboot.common.demo.combinator;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Copy from <a href="https://dzone.com/articles/introducing-combinators-part-2">Introducing Combinators series</a>
 *
 * @author solarknight created on 2018/4/7 11:56
 * @version 1.0
 */
@FunctionalInterface
public interface Provided<T, R> extends Function<Predicate<T>,
    Function<Function<T, R>,
        Function<Function<T, R>,
            Function<T, R>>>> {

  static <T, R> Provided<T, R> create() {
    return condition -> function -> fallback ->
        arg -> (condition.test(arg) ? function : fallback).apply(arg);
  }

  static <T, R> Function<T, R> decorate(
      Predicate<T> condition,
      Function<T, R> function,
      Function<T, R> fallback) {
    return Provided.<T, R>create().apply(condition).apply(function).apply(fallback);
  }
}
