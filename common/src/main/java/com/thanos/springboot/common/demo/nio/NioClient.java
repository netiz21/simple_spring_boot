package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
public class NioClient {
  private static final Logger logger = LoggerFactory.getLogger(NioClient.class);

  public static final int DEFAULT_PORT = 8080;

  private int port = DEFAULT_PORT;

  private Selector selector;

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

      EventBus eventBus = new EventBus();
      eventBus.register(this);

      logger.info("Client started...");

      // listen client input
      new Thread(new ClientInputListener(eventBus)).start();

      // listen server resp
      persistentListen(eventBus);

    } catch (Exception e) {
      logger.error("Fail to start client", e);
    }
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

  private void persistentListen(EventBus eventBus) {
    while (true) {
      int readyChannels = 0;
      try {
        readyChannels = selector.select(TimeUnit.SECONDS.toMillis(1));

        if (readyChannels == 0) {
          continue;
        }

        Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
        while (keyIterator.hasNext()) {
          SelectionKey key = keyIterator.next();

          if (key.isReadable()) {
            readKey(eventBus, key);
          }
          keyIterator.remove();
        }
      } catch (IOException e) {
        logger.error("Error process client input", e);
      }
    }
  }

  private void readKey(EventBus eventBus, SelectionKey key) throws IOException {
    SocketChannel channel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    while (channel.read(buffer) > 0) {
      logger.info("Receive content = {}", new String(buffer.array(), StandardCharsets.UTF_8));
      eventBus.post(newRespEvent(new String(buffer.array(), StandardCharsets.UTF_8)));
      buffer.clear();
    }
  }

  @Subscribe
  public void handleClientInput(ClientInputEvent event) {
    int readyChannels = 0;
    try {
      readyChannels = selector.select(TimeUnit.SECONDS.toMillis(1));

      if (readyChannels == 0) {
        logger.error("No available channels");
        return;
      }

      Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
      while (keyIterator.hasNext()) {
        SelectionKey key = keyIterator.next();

        if (key.isWritable()) {
          SocketChannel channel = (SocketChannel) key.channel();
          channel.write(ByteBuffer.wrap(event.message().getBytes()));
        }
        keyIterator.remove();
      }

    } catch (IOException e) {
      logger.error("Error process client input", e);
    }
  }

  @Subscribe
  public void handleServerResp(ServerRespEvent event) {
    System.out.println(event.message());
  }

  private static class ClientInputListener implements Runnable {
    private EventBus eventBus;

    public ClientInputListener(EventBus eventBus) {
      this.eventBus = eventBus;
    }

    @Override
    public void run() {
      Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
      while (true) {
        String msg = scanner.nextLine();
        // send message
        eventBus.post(newInputEvent(msg));
      }
    }
  }

  private static ClientInputEvent newInputEvent(String message) {
    return new ClientInputEvent(message);
  }

  private static ServerRespEvent newRespEvent(String message) {
    return new ServerRespEvent(message);
  }

  private static class ClientInputEvent implements MessageEvent {
    private String message;

    public ClientInputEvent(String message) {
      this.message = message;
    }

    @Override
    public String message() {
      return message;
    }
  }

  private static class ServerRespEvent implements MessageEvent {
    private String message;

    public ServerRespEvent(String message) {
      this.message = message;
    }

    @Override
    public String message() {
      return message;
    }
  }

  public static void main(String[] args) {
    NioClient client = NioClient.newClient();
    client.startAndListen();
  }
}
