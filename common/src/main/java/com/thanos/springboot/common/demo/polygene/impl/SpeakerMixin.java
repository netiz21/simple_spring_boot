package com.thanos.springboot.common.demo.polygene.impl;

import com.thanos.springboot.common.demo.polygene.Speaker;

/**
 * @author solarknight created on 17/5/25 下午5:13
 * @version 1.0
 */
public class SpeakerMixin implements Speaker {

  public static final String HELLO_WORLD = "Hello world";

  @Override
  public String sayHello() {
    return HELLO_WORLD;
  }
}
