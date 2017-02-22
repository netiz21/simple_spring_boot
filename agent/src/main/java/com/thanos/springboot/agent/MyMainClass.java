package com.thanos.springboot.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author peiheng.zph created on 17/1/23 下午11:51
 * @version 1.0
 */
public class MyMainClass {

  private static final Logger logger = LoggerFactory.getLogger(MyMainClass.class);

  static {
    CustomJavaAgent.initialize();
  }

  /**
   * Main method.
   */
  public static void main(String[] args) {
    logger.info("main method invoked with args: {}", Arrays.asList(args));

    int i = 5;
    Integer j = 3;
    logger.info("int i size is {}", CustomJavaAgent.sizeOf(i));
    logger.info("Integer j size is {}", CustomJavaAgent.sizeOf(j));

    long l = 5L;
    double d = 3.0;
    logger.info("long l size is {}", CustomJavaAgent.sizeOf(l));
    logger.info("double d size is {}", CustomJavaAgent.sizeOf(d));

    int[] array1 = new int[1];
    logger.info("int array with length {} consume size is {}", array1.length,
        CustomJavaAgent.sizeOf(array1));
    int[] array2 = new int[2];
    logger.info("int array with length {} consume size is {}", array2.length,
        CustomJavaAgent.sizeOf(array2));
    int[] array3 = new int[3];
    logger.info("int array with length {} consume size is {}", array3.length,
        CustomJavaAgent.sizeOf(array3));
    int[] array4 = new int[4];
    logger.info("int array with length {} consume size is {}", array4.length,
        CustomJavaAgent.sizeOf(array4));
    int[] array5 = new int[5];
    logger.info("int array with length {} consume size is {}", array5.length,
        CustomJavaAgent.sizeOf(array5));
    int[] array8 = new int[8];
    logger.info("int array with length {} consume size is {}", array8.length,
        CustomJavaAgent.sizeOf(array8));

    boolean b = false;
    logger.info("boolean b size is {}", CustomJavaAgent.sizeOf(b));

    Object o = new Object();
    logger.info("new object instance size is {}", CustomJavaAgent.sizeOf(o));

    MyUser user = new MyUser();
    logger.info("new MyUser instance size is {}", CustomJavaAgent.sizeOf(user));
  }

}
