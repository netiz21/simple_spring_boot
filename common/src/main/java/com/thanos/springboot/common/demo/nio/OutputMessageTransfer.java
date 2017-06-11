package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface OutputMessageTransfer extends MessageTransfer {
  void addEncoder(MessageEncoder encoder);

  void sendMessage(byte[] message, SocketChannel channel) throws IOException;
}
