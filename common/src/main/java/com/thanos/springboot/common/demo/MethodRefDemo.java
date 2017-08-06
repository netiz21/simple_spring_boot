package com.thanos.springboot.common.demo;

import java.util.function.Consumer;

/**
 * @author solarknight created on 2017/7/12 22:50
 * @version 1.0
 */
public class MethodRefDemo {
  public static void main(String[] args) {
    Consumer<String> c = System.out::println;

    System.out.println("====== Splitter ======");

    Consumer<String> c2 = new Consumer<String>() {
      @Override
      public void accept(String s) {
        System.out.println(s);
      }
    };
  }
}
