package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/5/17 下午6:36
 * @version 1.0
 */
public class SocketChannelDemo {

  public static void connectSocket() throws IOException, InterruptedException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);

    channel.connect(new InetSocketAddress(8080));
    while (!channel.finishConnect()) {
      TimeUnit.MILLISECONDS.sleep(100);
    }

    String text = "Hello world";
    ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());
    while (buffer.hasRemaining()) {
      channel.write(buffer);
    }

    channel.close();
  }

  public static void main(String[] args) {
    try {
      connectSocket();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
