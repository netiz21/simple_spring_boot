package com.thanos.springboot.common.demo.combinator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author peiheng.zph created on 18/2/1 下午7:00
 * @version 1.0
 */
public interface Combinator<T, R> extends Function<T, R> {

  static <T, R> Combinator<T, R> target(Function<T, R> function) {
    return function::apply;
  }

  default Combinator<T, R> before(Consumer<T> consumer) {
    return it -> {
      consumer.accept(it);
      return this.apply(it);
    };
  }

  default Combinator<T, R> after(BiConsumer<T, R> biConsumer) {
    return it -> {
      R r = this.apply(it);
      biConsumer.accept(it, r);
      return r;
    };
  }

  default Supplier<R> supplier(Supplier<T> supplier) {
    return () -> this.apply(supplier.get());
  }

}
