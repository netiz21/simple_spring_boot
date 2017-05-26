package com.thanos.springboot.common.demo.nio;

/**
 * @author solarknight created on 17/5/24 上午11:26
 * @version 1.0
 */
public interface MessageTransport {

  boolean offer(byte[] bytes);

  byte[] poll();
}
