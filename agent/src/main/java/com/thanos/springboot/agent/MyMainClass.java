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
    logger.info("String size is {}", CustomJavaAgent.sizeOf("Hello, world!"));
    logger.info("MyUser instance size is {}", CustomJavaAgent.sizeOf(new MyUser()));

    int i = 5;
    Integer j = 3;
    logger.info("int i size is {}", CustomJavaAgent.sizeOf(i));
    logger.info("Integer j size is {}", CustomJavaAgent.sizeOf(j));

    int[] array2 = new int[8];
    logger.info("int array with length {} consume size is {}", array2.length,
        CustomJavaAgent.sizeOf(array2));
    int[] array = new int[16];
    logger.info("int array with length {} consume size is {}", array.length,
        CustomJavaAgent.sizeOf(array));
    int[] array3 = new int[32];
    logger.info("int array with length {} consume size is {}", array3.length,
        CustomJavaAgent.sizeOf(array3));

    boolean b = false;
    logger.info("boolean b size is {}", CustomJavaAgent.sizeOf(b));

    Object o = new Object();
    logger.info("new object instance size is {}", CustomJavaAgent.sizeOf(o));
  }

}
