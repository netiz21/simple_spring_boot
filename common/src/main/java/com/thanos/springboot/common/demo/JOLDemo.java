package com.thanos.springboot.common.demo;

import com.google.common.util.concurrent.Uninterruptibles;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/4/15 下午11:36
 * @version 1.0
 */
public class JOLDemo {
  public static void main(String[] args) {
    ClassLayout layout = ClassLayout.parseClass(Object.class);
    Object object = new Object();
    System.out.println(layout.toPrintable(object));

    synchronized (object) {
      System.out.println(layout.toPrintable(object));
    }

    Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
    synchronized (object) {
      System.out.println(layout.toPrintable(object));
    }

    System.out.println(layout.toPrintable(object));

    System.out.println(Integer.toHexString(object.hashCode()));
    System.out.println(layout.toPrintable(object));
  }
}
