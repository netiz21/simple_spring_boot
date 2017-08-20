package com.thanos.springboot.common.demo;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author solarknight created on 2017/8/20 22:47
 * @version 1.0
 */
public class CheckedLambdaDemo {

  public static void main(String[] args) {
    originDemo();
    hacked1();
    hacked2();
  }

  private static void originDemo() {
    // Origin code can't compile
    Stream.of("http://localhost/", "https://github.com")
        // .map(URL::new)
        .collect(Collectors.toList());
  }

  private static void hacked1() {
    Stream.of("http://localhost/", "https://github.com")
         .map(it -> uncheckedCall(() -> new URL(it)))
        .collect(Collectors.toList());
  }

  private static void hacked2() {
    // TODO: 2017/8/20 update
  }

  public static <T> T uncheckedCall(Callable<T> callable) {
    try { return callable.call(); }
    catch (Exception e) { return sneakyThrow(e); }
  }

  private static <E extends Throwable, T> T sneakyThrow0(Throwable t) throws E { throw (E)t; }

  public static <T> T sneakyThrow(Throwable e) {
    return sneakyThrow0(e);
  }

}
