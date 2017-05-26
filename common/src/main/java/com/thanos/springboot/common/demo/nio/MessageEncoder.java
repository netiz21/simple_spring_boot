package com.thanos.springboot.common.demo.nio;

import java.nio.ByteBuffer;

/**
 * @author solarknight created on 17/5/24 上午11:16
 * @version 1.0
 */
public interface MessageEncoder {
  ByteBuffer encode(byte[] bytes);
}
