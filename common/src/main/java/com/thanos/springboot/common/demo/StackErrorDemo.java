package com.thanos.springboot.common.demo;

/**
 * @author peiheng.zph created on 17/2/22 上午11:00
 * @version 1.0
 */
public class StackErrorDemo {

  public void overFlow1(int idx) {
    idx++;
    System.out.println(idx);
    overFlow1(idx);
  }

  public void overFlow2(int idx1, int idx2) {
    idx1++;
    System.out.println(idx1);
    overFlow2(idx1, idx2);
  }

  public void overFlow3(int idx1, int idx2, int idx3) {
    idx1++;
    System.out.println(idx1);
    overFlow3(idx1, idx2, idx3);
  }

  public static void main(String[] args) {
    StackErrorDemo demo = new StackErrorDemo();
    demo.overFlow1(0);
//    demo.overFlow2(0, 0);
//    demo.overFlow3(0, 0, 0);
  }
}
