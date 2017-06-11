package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author solarknight created on 2017/6/4 下午5:54
 * @version 1.0
 */
public class NioServer implements MessageReceiver {
  private static final Logger logger = LoggerFactory.getLogger(NioServer.class);

  public static final int DEFAULT_PORT = 8080;

  private int port = DEFAULT_PORT;

  private Selector selector;

  private Map<SocketChannel, PipeMessageTransfer> channelTransferMap = new HashMap<>();

  public static NioServer newServer() {
    return new NioServer();
  }


  public void startAndListen() {
    ServerSocketChannel channel = openNonBlockingServerChannel(port);
    if (channel == null) {
      return;
    }

    try {
      selector = Selector.open();
      channel.register(selector, SelectionKey.OP_ACCEPT);

      logger.info("Server started...");

      // listen client request
      persistentListen();

    } catch (Exception e) {
      logger.error("Fail to start server", e);
    }
  }

  private void persistentListen() {
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

          if (key.isAcceptable()) {
            acceptKey(key);

          } else if (key.isReadable()) {
            readKey(key);

          } else if (key.isWritable()) {
            writeKey(key);
          }

          keyIterator.remove();
        }
      } catch (IOException e) {
        logger.error("Error process client input", e);
      }
    }
  }

  private void writeKey(SelectionKey key) throws IOException {
    logger.debug("Start write resp to client");

    SocketChannel channel = (SocketChannel) key.channel();
    if (!channel.isConnected()) {
      logger.info("Connection broken, return");
    }

    channelTransferMap.get(channel).sendMessage("Hello from server".getBytes(), channel);
    key.interestOps(SelectionKey.OP_READ);
  }

  private void readKey(SelectionKey key) throws IOException {
    logger.debug("Start read from client input");

    SocketChannel channel = (SocketChannel) key.channel();
    if (channelTransferMap.get(channel).receiveMessage(channel)) {
      channel.register(selector, SelectionKey.OP_WRITE);
    }
  }

  @Subscribe
  @Override
  public void receive(byte[] message) {
    logger.info("==================================================");
    logger.info("Receive content = {}", new String(message, StandardCharsets.UTF_8));
    logger.info("==================================================");
  }

  private void acceptKey(SelectionKey key) throws IOException {
    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
    SocketChannel socketChannel = serverChannel.accept();
    logger.debug("Setup channel with client");

    socketChannel.configureBlocking(false);
    socketChannel.register(this.selector, SelectionKey.OP_READ);

    PipeMessageTransfer transfer = buildMessageTransfer();
    transfer.registerReceiver(this);
    channelTransferMap.put(socketChannel, transfer);
  }

  private PipeMessageTransfer buildMessageTransfer() {
    PipeMessageTransfer messageTransfer = Messages.newPipeMessageTransfer();
    messageTransfer.addEncoder(Messages.newFixedHeadMessageEncoder());
    messageTransfer.addDecoder(Messages.newFixedHeadMessageDecoder());
    return messageTransfer;
  }

  private ServerSocketChannel openNonBlockingServerChannel(int port) {
    try {
      ServerSocketChannel serverChannel = ServerSocketChannel.open();
      serverChannel.socket().bind(new InetSocketAddress(port));
      serverChannel.configureBlocking(false);
      return serverChannel;

    } catch (IOException e) {
      logger.error("Failed to bind server socket, port = {}", port);
      return null;
    }
  }

  public static void main(String[] args) {
    NioServer server = NioServer.newServer();
    server.startAndListen();
  }
}
