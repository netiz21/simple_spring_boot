package com.thanos.springboot.common.demo.nio;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface OutputMessageTransfer extends MessageTransfer {
  void addEncoder(MessageEncoder encoder);

  void sendMessage(byte[] message);
}
