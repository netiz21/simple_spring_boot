package com.thanos.springboot.common.demo.lambda;

public interface LifecycleAware {

  default void start() {}

  default void stop() {}

}
