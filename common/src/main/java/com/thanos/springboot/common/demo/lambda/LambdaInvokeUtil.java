package com.thanos.springboot.common.demo.lambda;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * @author peiheng.zph created on 17/8/22 上午11:12
 * @version 1.0
 */
public final class LambdaInvokeUtil {

  @FunctionalInterface
  private interface SilentInvoker extends Function<Callable<?>, Object> {
    MethodType SIGNATURE = MethodType.methodType(Object.class, Callable.class);//signature after type erasure

    <V> V invoke(final Callable<V> callable);

    @Override
    default Object apply(final Callable<?> callable) {
      return invoke(callable);
    }

    static <V> V call(final Callable<V> callable) throws Exception {
      return callable.call();
    }
  }

  private static final SilentInvoker SILENT_INVOKER;

  static {
    final MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      final CallSite site = LambdaMetafactory.metafactory(lookup,
          "invoke",
          MethodType.methodType(SilentInvoker.class),
          SilentInvoker.SIGNATURE,
          lookup.findStatic(SilentInvoker.class, "call", SilentInvoker.SIGNATURE),
          SilentInvoker.SIGNATURE);
      SILENT_INVOKER = (SilentInvoker) site.getTarget().invokeExact();
    } catch (final Throwable e) {
      throw new ExceptionInInitializerError(e);
    }
  }


  public static <V> V callUnchecked(final Callable<V> callable) /*no throws*/ {
    return SILENT_INVOKER.invoke(callable);
  }
}
