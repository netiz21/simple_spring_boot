package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author peiheng.zph created on 17/5/17 下午7:09
 * @version 1.0
 */
public class ServerSocketChannelDemo {
  private static final int PACKET_HEADER_LENGTH = 4;

  public static void startServer() throws IOException, InterruptedException {
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    serverChannel.socket().bind(new InetSocketAddress(8080));
    serverChannel.configureBlocking(false);

    while (true) {
      SocketChannel socketChannel = serverChannel.accept();
      if (socketChannel == null) {
        TimeUnit.MILLISECONDS.sleep(100);
        continue;
      }

      new Thread(() -> {
        try {
          handleChannel(socketChannel);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();
    }
  }

  private static void handleChannel(SocketChannel socketChannel) throws IOException {
    System.out.println("Accept connect channel");

    List<Byte> packetContent = new ArrayList<>();
    int packetLength = 0;

    ByteBuffer buffer = ByteBuffer.allocate(64);
    while (socketChannel.read(buffer) != -1) {
      buffer.flip();
      if (buffer.remaining() < PACKET_HEADER_LENGTH) {
        buffer.compact();
        continue;
      }

      if (packetLength == 0) {
        packetLength = buffer.getInt();
      }

      while (packetContent.size() < packetLength && buffer.hasRemaining()) {
        packetContent.add(buffer.get());
      }

      if (packetContent.size() == packetLength) {
        byte[] array = toPrimitive(packetContent);
        String encoded = new String(array, StandardCharsets.UTF_8);
        System.out.println(encoded);
        System.out.println("Process tcp packet complete");

        packetContent.clear();
        packetLength = 0;
      }

      buffer.compact();
    }
  }

  public static byte[] toPrimitive(List<Byte> list) {
    if (list == null) {
      return null;
    }
    byte[] array = new byte[list.size()];
    for (int i = 0; i < array.length; i++) {
      array[i] = list.get(i);
    }
    return array;
  }

  public static void main(String[] args) {
    try {
      startServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
