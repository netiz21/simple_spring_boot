package com.thanos.springboot.common.demo;

/**
 * @author solarknight created on 2017/7/9 19:07
 * @version 1.0
 */
public class KotlinInteractDemo {
  public static void main(String[] args) {
    System.out.println(DemoUtils.twice(10));
    DemoUtils2.INSTANCE.echo("Hello world");
  }
}
