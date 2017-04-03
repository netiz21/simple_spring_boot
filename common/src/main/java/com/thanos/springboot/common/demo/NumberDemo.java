package com.thanos.springboot.common.demo;

/**
 * @author peiheng.zph created on 2017/4/3 11:58
 * @version 1.0
 */
public class NumberDemo {
    public static void main(String[] args) {
        System.out.println("Max Integer: " + Integer.MAX_VALUE);
        System.out.println("Min Integer: " + Integer.MIN_VALUE);

        int i = (1 << 31) - 1;
        System.out.println(i);
        // overflow
        int j = 1 << 31;
        System.out.println(j);
        System.out.println(Math.abs(j));

        System.out.println(Double.POSITIVE_INFINITY);
        System.out.println(Double.NEGATIVE_INFINITY);

        System.out.println(1.0 / 0);
        // runtime exception
        // System.out.println(1 / 0);

        // a % b = a - (a / b) * b
        System.out.println("-14 / 3 = " + -14 / 3 + ", -14 % 3 = " + -14 % 3);
        System.out.println("14 / -3 = " + 14 / -3 + ", 14 % -3 = " + 14 % -3);

        // convert to string "64.0" during compile time
        System.out.println(1 + 2 + 3 + "4.0");
    }
}
