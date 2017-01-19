package com.thanos.springboot.common.demo;

import com.google.common.base.Function;

/**
 * @author peiheng.zph created on 2016/12/28 23:49
 * @version 1.0
 */
public class CurryDemo {

  public static Function add(final int x) {
    return new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return x + input;
      }
    };
  }


  public static void main(String[] args) {
    Function<Integer, Integer> incr = add(1);
    Function<Integer, Integer> dIncr = add(2);

    System.out.println(incr.apply(10));
    System.out.print(dIncr.apply(10));
  }
}
