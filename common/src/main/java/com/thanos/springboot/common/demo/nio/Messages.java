package com.thanos.springboot.common.demo.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author solarknight created on 17/5/24 上午11:15
 * @version 1.0
 */
public final class Messages {

  private static final int HEAD_LEN = 4;

  public static MessageTransport newTransport(SocketChannel channel) {
    return null;
  }

  public static MessageEncoder newEncoder(SocketChannel channel) {
    return new FixedHeadMessageEncoder(channel);
  }

  public static MessageDecoder newDecoder(SocketChannel channel) {
    return new FixedHeadMessageDecoder(channel);
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
