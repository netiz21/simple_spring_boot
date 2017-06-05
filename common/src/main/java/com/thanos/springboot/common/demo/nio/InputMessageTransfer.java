package com.thanos.springboot.common.demo.nio;

/**
 * Created by solarknight on 2017/6/5.
 */
public interface InputMessageTransfer extends MessageTransfer {
  void addDecoder(MessageDecoder decoder);

  void registerReceiver(Object receiver);
}
