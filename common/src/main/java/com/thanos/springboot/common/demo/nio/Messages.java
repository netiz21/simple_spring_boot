package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.EventBus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author solarknight created on 17/5/24 上午11:15
 * @version 1.0
 */
public final class Messages {

  private static final int MESSAGE_HEAD_LEN = 4;

  public static PipeMessageTransfer newPipeMessageTransfer() {
    return new SimplePipeMessageTransfer();
  }

  public static MessageEncoder newFixedHeadMessageEncoder() {
    return new FixedHeadMessageEncoder();
  }

  public static MessageDecoder newFixedHeadMessageDecoder() {
    return new FixedHeadMessageDecoder();
  }

  private static class SimplePipeMessageTransfer implements PipeMessageTransfer {
    private MessageEncoder encoder;
    private MessageDecoder decoder;

    private EventBus eventBus = new EventBus();

    @Override
    public void addDecoder(MessageDecoder decoder) {
      if (decoder == null) {
        throw new IllegalArgumentException();
      }
      this.decoder = decoder;
    }

    @Override
    public void registerReceiver(MessageReceiver receiver) {
      if (receiver == null) {
        throw new IllegalArgumentException();
      }
      eventBus.register(receiver);
    }

    @Override
    public boolean receiveMessage(SocketChannel channel) throws IOException {
      boolean complete = false;
      ByteBuffer buffer = ByteBuffer.allocate(64);
      while (channel.read(buffer) > 0) {
        buffer.flip();

        byte[] content = decoder.decode(buffer);
        if (content != null) {
          complete = true;
          eventBus.post(content);
        }
        buffer.compact();
      }
      return complete;
    }

    @Override
    public void addEncoder(MessageEncoder encoder) {
      if (encoder == null) {
        throw new IllegalArgumentException();
      }
      this.encoder = encoder;
    }

    @Override
    public void sendMessage(byte[] message, SocketChannel channel) throws IOException {
      channel.write(encoder.encode(message));
    }
  }

  private static class FixedHeadMessageEncoder implements MessageEncoder {
    @Override
    public ByteBuffer encode(byte[] bytes) {
      ByteBuffer buffer = ByteBuffer.allocate(bytes.length + MESSAGE_HEAD_LEN);
      buffer.putInt(bytes.length).put(bytes);
      buffer.flip();
      return buffer;
    }
  }

  private static class FixedHeadMessageDecoder implements MessageDecoder {
    private List<Byte> packetContent = new ArrayList<>();
    private int packetLength = 0;

    @Override
    public byte[] decode(ByteBuffer buffer) {
      if (packetLength == 0 && buffer.remaining() < MESSAGE_HEAD_LEN) {
        return null;
      }
      if (packetLength == 0 && buffer.remaining() > MESSAGE_HEAD_LEN) {
        packetLength = buffer.getInt();
      }

      while (packetContent.size() < packetLength && buffer.hasRemaining()) {
        packetContent.add(buffer.get());
      }

      if (packetContent.size() == packetLength) {
        byte[] array = toPrimitive(packetContent);
        packetContent.clear();
        packetLength = 0;

        return array;
      }
      return null;
    }

    private static byte[] toPrimitive(List<Byte> list) {
      if (list == null) {
        return null;
      }
      byte[] array = new byte[list.size()];
      for (int i = 0; i < array.length; i++) {
        array[i] = list.get(i);
      }
      return array;
    }
  }

  private Messages() {
  }
}
