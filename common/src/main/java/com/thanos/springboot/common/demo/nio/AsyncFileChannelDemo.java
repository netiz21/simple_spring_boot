package com.thanos.springboot.common.demo.nio;

import com.google.common.util.concurrent.Uninterruptibles;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * Created by solarknight on 2017/5/21.
 */
public class AsyncFileChannelDemo {

  public static void main(String[] args) {
    try {
      readFile();
      Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void readFile() throws IOException {
    Path path = Paths.get(".", "pom.xml");

    AsynchronousFileChannel fileChannel =
        AsynchronousFileChannel.open(path, StandardOpenOption.READ);
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
      @Override
      public void completed(Integer result, ByteBuffer attachment) {
        System.out.println("Read bytes " + result);
        System.out.println(new String(attachment.array(), StandardCharsets.UTF_8));
      }

      @Override
      public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
      }
    });
  }
}
