package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author solarknight created on 17/5/17 下午3:26
 * @version 1.0
 */
public class SelectorDemo {

  public static void sampleUsage() throws IOException {
    Selector selector = Selector.open();

    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);

    channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

    while (true) {

      int readyChannels = selector.selectNow();

      if (readyChannels == 0) continue;

      Set<SelectionKey> selectedKeys = selector.selectedKeys();

      Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

      while (keyIterator.hasNext()) {

        SelectionKey key = keyIterator.next();

        if (key.isAcceptable()) {
          // a connection was accepted by a ServerSocketChannel.

        } else if (key.isConnectable()) {
          // a connection was established with a remote server.

        } else if (key.isReadable()) {
          // a channel is ready for reading

        } else if (key.isWritable()) {
          // a channel is ready for writing
        }

        keyIterator.remove();
      }
    }
  }

  public static void main(String[] args) {
    try {
      sampleUsage();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
