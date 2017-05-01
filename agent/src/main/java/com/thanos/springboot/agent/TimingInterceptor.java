package com.thanos.springboot.agent;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by solarknight on 2017/5/1.
 */
public class TimingInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(CustomJavaAgent.class);

  @RuntimeType
  public static Object intercept(@Origin Method method,
                                 @SuperCall Callable<?> callable) throws Exception {
    long start = System.currentTimeMillis();
    try {
      return callable.call();
    } finally {
      logger.info("{} invoke took {}ms", method, (System.currentTimeMillis() - start));
    }
  }
}
