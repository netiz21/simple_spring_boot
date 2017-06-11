package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.Subscribe;

/**
 * Created by solarknight on 2017/6/11.
 */
public interface MessageReceiver {
  @Subscribe
  void receive(byte[] message);
}
