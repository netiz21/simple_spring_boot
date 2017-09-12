package com.thanos.springboot.common.demo.lambda;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.thanos.springboot.common.demo.lambda.LambdaExceptionUtil.rethrowFunction;
import static com.thanos.springboot.common.demo.lambda.LambdaUtils.callUnchecked;
import static com.thanos.springboot.common.demo.lambda.SimpleUtil.uncheckCall;

/**
 * @author peiheng.zph created on 17/8/21 下午9:08
 * @version 1.0
 */
public class CheckedLambdaDemo {

  public static void main(String[] args) {
    originCode();
    hackedCode1();
    hackedCode2();
    hackedCode3();
  }

  private static void originCode() {
    Arrays.asList("https://www.baidu.com", "https://www.google.com")
        .stream()
        // can't compile
        // .map(URL::new)
        .collect(Collectors.toList());
  }

  private static void hackedCode1() {
    Arrays.asList("https://www.baidu.com", "https://www.google.com")
        .stream()
        .map(it -> uncheckCall(() -> new URL(it)))
        .collect(Collectors.toList());
  }

  private static void hackedCode2() {
    Arrays.asList("https://www.baidu.com", "https://www.google.com")
        .stream()
        .map(rethrowFunction(URL::new))
        .collect(Collectors.toList());
  }

  private static void hackedCode3() {
    Arrays.asList("https://www.baidu.com", "https://www.google.com")
        .stream()
        .map(it -> callUnchecked(() -> new URL(it)))
        .collect(Collectors.toList());
  }
}
