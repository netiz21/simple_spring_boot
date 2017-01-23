package com.thanos.springboot.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author peiheng.zph created on 17/1/23 下午11:51
 * @version 1.0
 */
public class MyMainClass {

  static final Logger logger = LoggerFactory.getLogger(MyMainClass.class);

  static {
    CustomJavaAgent.initialize();
  }

  /**
   * Main method.
   *
   * @param args
   */
  public static void main(String[] args) {
    logger.info("main method invoked with args: {}", Arrays.asList(args));
    logger.info("String size is {}", CustomJavaAgent.sizeOf("Hello, world!"));
  }

}
