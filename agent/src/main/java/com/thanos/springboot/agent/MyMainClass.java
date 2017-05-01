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

    Integer j = 3;
    logger.info("Integer j size is {}", CustomJavaAgent.sizeOf(j));

    Double d = 3.0;
    logger.info("Double d size is {}", CustomJavaAgent.sizeOf(d));

    Boolean b = false;
    logger.info("Boolean b size is {}", CustomJavaAgent.sizeOf(b));

    calculateArraySize();
    calculateObjectSize();
    calculateStringSize();

    TimedOperation operation = new TimedOperation();
    logger.info("incr result {}", operation.incrTimed());
  }

  private static void calculateObjectSize() {
    Object o = new Object();
    logger.info("new object instance size is {}", CustomJavaAgent.sizeOf(o));

    MyUser user = new MyUser();
    logger.info("new MyUser instance size is {}", CustomJavaAgent.sizeOf(user));
  }

  private static void calculateStringSize() {
    // 64bit jvm with Coops: 12 [object header] + 4 [pointer to char array] + 4 [int hash value] + 4 [padding] = 24
    String s = "hello";
    logger.info("String '{}' size is {}", s, CustomJavaAgent.sizeOf(s));

    // 64bit jvm with Coops: 16 [array header] + 5 * 2 [char] + 6 [padding] = 32
    char[] chars = {'h', 'e', 'l', 'l', 'o'};
    logger.info("Char array '{}' size is {}", chars, CustomJavaAgent.sizeOf(chars));
  }

  private static void calculateArraySize() {
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
  }

}
