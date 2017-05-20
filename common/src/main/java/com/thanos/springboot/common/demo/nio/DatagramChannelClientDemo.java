package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by solarknight on 2017/5/20.
 */
public class DatagramChannelClientDemo {

  public static void startClient() throws IOException {
    InetSocketAddress addr = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
    DatagramChannel channel = DatagramChannel.open();

    String msg = "New String to write to file..."
        + System.currentTimeMillis();

    ByteBuffer buf = ByteBuffer.wrap(msg.getBytes());
    int bytesSent = channel.send(buf, addr);
    System.out.println("Send byte count " + bytesSent);
  }

  public static void main(String[] args) {
    try {
      startClient();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
