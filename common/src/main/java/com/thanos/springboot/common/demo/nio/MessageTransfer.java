package com.thanos.springboot.common.demo.nio;

import java.nio.channels.SocketChannel;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface MessageTransfer {
  void bind(SocketChannel channel);
}
