package com.thanos.springboot.common.demo;

/**
 * @author peiheng.zph created on 2017/1/21 13:27
 * @version 1.0
 */
public class StaticMethodRefDemo {
    interface Printer {
        void print(String val);
    }

    public void printSomething(String something, Printer printer) {
        printer.print(something);
    }

    public static void main(String[] args) {
        StaticMethodRefDemo demo = new StaticMethodRefDemo();
        String str = "Hello, world!";
        demo.printSomething(str, System.out::println);
    }
}
