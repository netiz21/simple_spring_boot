package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface InputMessageTransfer extends MessageTransfer {
  void addDecoder(MessageDecoder decoder);

  void registerReceiver(MessageReceiver receiver);

  boolean receiveMessage(SocketChannel channel) throws IOException;
}
