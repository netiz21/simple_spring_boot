package com.thanos.springboot.common.demo;

import com.google.common.base.Throwables;

import javax.naming.OperationNotSupportedException;

/**
 * @author peiheng.zph created on 17/2/14 上午10:52
 * @version 1.0
 */
public class ThrowableDemo {

  public static void badOperation() {
    System.out.println("Come on");
    Throwable t = new OperationNotSupportedException("Not supported");
    throw new RuntimeException(t);
  }

  public static void main(String[] args) {
    Throwable t = null;
    try {
      badOperation();
    } catch (Exception e) {
      t = e;
    }
    String stack = Throwables.getStackTraceAsString(t);
    System.out.println(stack);
  }
}
