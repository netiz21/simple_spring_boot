package com.thanos.springboot.common.demo.lambda;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/**
 * @author solarknight created on 2017/12/19 0:04
 * @version 1.0
 */
public class Currying {
  public static void main(String[] args) {
    IntFunction<IntUnaryOperator> curriedAdd = a -> b -> a + b;
    curriedAdd.apply(1).applyAsInt(2);
  }
}
