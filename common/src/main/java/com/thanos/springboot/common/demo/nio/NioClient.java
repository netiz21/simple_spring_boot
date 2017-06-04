package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by solarknight on 2017/6/4.
 */
public class NioClient {
  public static final int DEFAULT_PORT = 8088;

  private int port = DEFAULT_PORT;

  public static NioClient newClient() {
    return new NioClient();
  }

  private NioClient() {
  }

  public void startAndListen() {
    try {
      SocketChannel channel = openNonBlockingChannel(port);

      Scanner scanner = new Scanner(System.in);
      while (true) {
        String msg = scanner.nextLine();
        // send message
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private SocketChannel openNonBlockingChannel(int port) throws IOException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);

    channel.connect(new InetSocketAddress(port));
    return channel;
  }

  public static void main(String[] args) {
    NioClient client = NioClient.newClient();
    client.startAndListen();
  }
}
