package com.thanos.springboot.common.demo.lambda;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Lambda util from project snamp {@see https://bitbucket.org/bytex-solutions/snamp/src/master/framework/src/main/java/com/bytex/snamp/internal/Utils.java?fileviewer=file-view-default}
 *
 * @author peiheng.zph created on 17/8/22 上午11:12
 * @version 1.0
 */
public final class LambdaUtils {

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

  public static Function createGetter(final MethodHandles.Lookup lookup,
                                      final MethodHandle getter) throws Exception {
    final CallSite site = LambdaMetafactory.metafactory(lookup, "apply",
        MethodType.methodType(Function.class),
        MethodType.methodType(Object.class, Object.class), //signature of method Function.apply after type erasure
        getter,
        getter.type()); //actual signature of getter
    try {
      return (Function) site.getTarget().invokeExact();
    } catch (final Exception e) {
      throw e;
    } catch (final Throwable e) {
      throw new Error(e);
    }
  }

  public static BiConsumer createSetter(final MethodHandles.Lookup lookup,
                                        final MethodHandle setter) throws Exception {
    final CallSite site = LambdaMetafactory.metafactory(lookup,
        "accept",
        MethodType.methodType(BiConsumer.class),
        MethodType.methodType(void.class, Object.class, Object.class), //signature of method BiConsumer.accept after type erasure
        setter,
        setter.type()); //actual signature of setter
    try {
      return (BiConsumer) site.getTarget().invokeExact();
    } catch (final Exception e) {
      throw e;
    } catch (final Throwable e) {
      throw new Error(e);
    }
  }

}
