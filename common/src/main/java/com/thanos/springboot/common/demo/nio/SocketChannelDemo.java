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
    String text2 = " 你好啊你好啊你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好你好啊你好啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊";
    ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());
    ByteBuffer buffer2 = ByteBuffer.wrap(text2.getBytes());

    while (buffer.hasRemaining()) {
      channel.write(new ByteBuffer[]{buffer, buffer2});
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
