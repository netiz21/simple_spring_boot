package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/5/17 下午7:09
 * @version 1.0
 */
public class ServerSocketChannelDemo {

  public static void openSocket() throws IOException, InterruptedException {
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    serverChannel.socket().bind(new InetSocketAddress(8080));
    serverChannel.configureBlocking(false);

    while (true) {
      SocketChannel socketChannel = serverChannel.accept();
      if (socketChannel == null) {
        TimeUnit.MILLISECONDS.sleep(100);
        continue;
      }

      System.out.println("Accept connect channel");
      ByteBuffer buffer = ByteBuffer.allocate(64);

      while (socketChannel.read(buffer) != -1) {
        buffer.flip();
        while (buffer.hasRemaining()) {
          System.out.print((char) buffer.get());
        }
        buffer.compact();
      }

      System.out.println();
      System.out.println("Process connect channel complete");
    }

  }

  public static void main(String[] args) {
    try {
      openSocket();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
