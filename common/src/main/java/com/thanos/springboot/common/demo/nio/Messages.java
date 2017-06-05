package com.thanos.springboot.common.demo.nio;

import com.google.common.eventbus.EventBus;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author solarknight created on 17/5/24 上午11:15
 * @version 1.0
 */
public final class Messages {

  private static final int HEAD_LEN = 4;

  public static InputMessageTransfer newInputMessageTransfer() {
    return new SimpleInputMessageTransfer();
  }

  public static OutputMessageTransfer newOutputMessageTransfer() {
    return new SimpleOutputMessageTransfer();
  }

  public static MessageEncoder newEncoder(SocketChannel channel) {
    return new FixedHeadMessageEncoder(channel);
  }

  public static MessageDecoder newDecoder(SocketChannel channel) {
    return new FixedHeadMessageDecoder(channel);
  }

  private static class SimpleInputMessageTransfer implements InputMessageTransfer {
    private List<MessageDecoder> decoders = new ArrayList<>();
    private EventBus eventBus = new EventBus();

    @Override
    public void addDecoder(MessageDecoder decoder) {
      if (decoder == null) {
        throw new IllegalArgumentException("Decoder can't be null");
      }
      this.decoders.add(decoder);
    }

    @Override
    public void registerReceiver(Object receiver) {
      eventBus.register(receiver);
    }

    @Override
    public void bind(SocketChannel channel) {

    }
  }

  private static class SimpleOutputMessageTransfer implements OutputMessageTransfer {

    @Override
    public void addEncoder(MessageEncoder encoder) {

    }

    @Override
    public void sendMessage(byte[] message) {

    }

    @Override
    public void bind(SocketChannel channel) {

    }
  }

  private static class FixedHeadMessageEncoder implements MessageEncoder {
    private SocketChannel channel;

    public FixedHeadMessageEncoder(SocketChannel channel) {
      this.channel = channel;
    }

    @Override
    public ByteBuffer encode(byte[] bytes) {
      ByteBuffer buffer = ByteBuffer.allocate(bytes.length + HEAD_LEN);
      buffer.putInt(bytes.length).put(bytes);
      buffer.flip();
      return buffer;
    }
  }

  private static class FixedHeadMessageDecoder implements MessageDecoder {
    private SocketChannel channel;

    public FixedHeadMessageDecoder(SocketChannel channel) {
      this.channel = channel;
    }

    @Override
    public byte[] decode(ByteBuffer buffer) {
      return new byte[0];
    }
  }

  private Messages() {
  }
}
