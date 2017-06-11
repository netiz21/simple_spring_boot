package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author solarknight created on 2017/6/4 上午10:12
 * @version 1.0
 */
public class NioClient implements MessageReceiver {
  private static final Logger logger = LoggerFactory.getLogger(NioClient.class);

  public static final int DEFAULT_PORT = 8080;

  private int port = DEFAULT_PORT;

  private Selector selector;

  private PipeMessageTransfer transfer;

  public static NioClient newClient() {
    return new NioClient();
  }

  private NioClient() {
  }

  public void startAndListen() {
    try {
      SocketChannel channel = openNonBlockingChannel(port);

      selector = Selector.open();
      channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

      transfer = buildMessageTransfer();
      transfer.registerReceiver(this);

      // listen client input
      new Thread(new ClientInputListener(this)).start();

      logger.info("Client started...");

      // listen server resp
      persistentListen();
    } catch (Exception e) {
      logger.error("Fail to start client", e);
    }
  }

  private PipeMessageTransfer buildMessageTransfer() {
    PipeMessageTransfer messageTransfer = Messages.newPipeMessageTransfer();
    messageTransfer.addEncoder(Messages.newFixedHeadMessageEncoder());
    messageTransfer.addDecoder(Messages.newFixedHeadMessageDecoder());
    return messageTransfer;
  }

  private SocketChannel openNonBlockingChannel(int port) throws IOException, InterruptedException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);

    channel.connect(new InetSocketAddress(port));
    while (!channel.finishConnect()) {
      TimeUnit.MILLISECONDS.sleep(100);
    }
    return channel;
  }

  private void persistentListen() {
    while (true) {
      int readyChannels = 0;
      try {
        readyChannels = selector.select(TimeUnit.SECONDS.toMillis(1));
      } catch (IOException e) {
        logger.error("Error select channels", e);
      }

      if (readyChannels == 0) {
        continue;
      }

      Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();

        if (key.isReadable()) {
          try {
            readKey(key);
          } catch (IOException e) {
            logger.error("Error process client input", e);
          }
        }
        keyIterator.remove();
      }
    }
  }

  private void readKey(SelectionKey key) throws IOException {
    SocketChannel channel = (SocketChannel) key.channel();
    transfer.receiveMessage(channel);
  }

  public void onClientInput(String msg) {
    int readyChannels = 0;
    try {
      readyChannels = selector.select(TimeUnit.SECONDS.toMillis(1));
    } catch (IOException e) {
      logger.error("Error process client input", e);
      return;
    }

    if (readyChannels == 0) {
      logger.error("No available channels, discard input message = {}", msg);
      return;
    }

    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
    while (keyIterator.hasNext()) {
      SelectionKey key = keyIterator.next();

      if (key.isWritable()) {
        try {
          transfer.sendMessage(msg.getBytes(), (SocketChannel) key.channel());
        } catch (IOException e) {
          logger.error("Error send message, content = {}", msg, e);
        }
      }
      keyIterator.remove();
    }
  }

  @Subscribe
  @Override
  public void receive(byte[] message) {
    logger.info("Receive message = {}", new String(message, StandardCharsets.UTF_8));
  }

  private static class ClientInputListener implements Runnable {
    private NioClient client;

    public ClientInputListener(NioClient client) {
      this.client = client;
    }

    @Override
    public void run() {
      Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
      while (true) {
        String msg = scanner.nextLine();
        // send message
        client.onClientInput(msg);
      }
    }
  }

  public static void main(String[] args) {
    NioClient client = NioClient.newClient();
    client.startAndListen();
  }
}
