package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Created by solarknight on 2017/5/20.
 */
public class DatagramChannelServerDemo {

  public static void startServer() throws IOException, InterruptedException {
    DatagramChannel channel = DatagramChannel.open();
    channel.socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), 8080));
    channel.configureBlocking(false);

    ByteBuffer buffer = ByteBuffer.allocate(64);
    while (true) {
      if (channel.receive(buffer) == null) {
        TimeUnit.MILLISECONDS.sleep(100);
        continue;
      }

      System.out.println("Process udp datagram begin");
      buffer.flip();

      String encoded = new String(buffer.array(), StandardCharsets.UTF_8);
      System.out.println(encoded);

      System.out.println("Process udp datagram complete");
      buffer.clear();
    }
  }

  public static void main(String[] args) {
    try {
      startServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
