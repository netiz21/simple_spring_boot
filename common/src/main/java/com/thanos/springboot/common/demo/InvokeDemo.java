package com.thanos.springboot.common.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author solarknight created on 2017/8/6 19:35
 * @version 1.0
 */
public class InvokeDemo {
  private static final Logger log = LoggerFactory.getLogger(InvokeDemo.class);

  public static void main(String[] args) {
    invokeVirtualMethod();
    invokeExact();
  }

  public static void invokeExact() {
    try {
      MethodType methodType = MethodType.methodType(int.class, new Class<?>[]{String.class});
      MethodHandles.Lookup lookup = MethodHandles.lookup();
      MethodHandle methodHandle = lookup.findStatic(Counter.class, "count", methodType);
      int count = (int) methodHandle.invokeExact("foo");
      log.info("foo char count is {}", count);
    } catch (Throwable t) {
      log.warn("Fail to invoke method", t);
    }
  }

  public static void invokeVirtualMethod() {
    HW hw = new HW();
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    try {
      MethodHandle mh1 = lookup.findVirtual(HW.class, "hello1", MethodType.methodType(void.class));
      mh1.invoke(hw);

      MethodHandle mh2 = lookup.findVirtual(HW.class, "hello2", MethodType.methodType(void.class));
      mh2.invoke(hw);
    } catch (Throwable t) {
      log.warn("Fail to invoke method", t);
    }
  }

  static class HW {
    public void hello1() {
      System.out.println("hello from hello1");
    }

    private void hello2() {
      System.out.println("hello from hello2");
    }
  }

  static class Counter {
    static int count(String name) {
      return name.length();
    }
  }
}
