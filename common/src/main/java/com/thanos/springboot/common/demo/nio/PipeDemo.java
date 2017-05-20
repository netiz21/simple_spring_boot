package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

/**
 * Created by solarknight on 2017/5/20.
 */
public class PipeDemo {

  public static void pipeInputOutput() throws IOException {
    Pipe pipe = Pipe.open();
    new Thread(() -> {
      Pipe.SinkChannel channel = pipe.sink();
      String msg = "Hello, world";
      ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

      try {
        while (buffer.hasRemaining()) {
          channel.write(buffer);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          channel.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

    new Thread(() -> {
      Pipe.SourceChannel channel = pipe.source();
      ByteBuffer buffer = ByteBuffer.allocate(64);

      try {
        while (channel.read(buffer) != -1) {
          System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  public static void main(String[] args) {
    try {
      pipeInputOutput();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
