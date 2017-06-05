package com.thanos.springboot.common.demo.nio;

import java.nio.ByteBuffer;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface Message {
  ByteBuffer[] toByteBuffer();
}
