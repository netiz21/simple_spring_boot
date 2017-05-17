package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author solarknight created on 17/5/16 下午7:32
 * @version 1.0
 */
public class FileChannelDemo {

  public static void readFile(String fileName) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(FileChannelDemo.class.getResource("/")
        .getPath() + fileName, "r");
    FileChannel inChannel = raf.getChannel();

    ByteBuffer buf = ByteBuffer.allocate(256);

    while (inChannel.read(buf) != -1) {
      buf.flip();

      CharBuffer tmp = StandardCharsets.UTF_8.decode(buf);
      while (tmp.hasRemaining()) {
        System.out.print(tmp.get());
      }

      buf.clear();
    }

    raf.close();
  }

  public static void repeatFile(String fileName) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(FileChannelDemo.class.getResource("/")
        .getPath() + fileName, "r");
    FileChannel inChannel = raf.getChannel();

    ByteBuffer buf = ByteBuffer.allocate(256);

    while (inChannel.read(buf) != -1) {
      buf.flip();

      CharBuffer tmp = StandardCharsets.UTF_8.decode(buf);
      while (tmp.hasRemaining()) {
        System.out.print(tmp.get());
      }

      // try rewind
      System.out.println();
      tmp.rewind();
      while (tmp.hasRemaining()) {
        System.out.print(tmp.get());
      }

      buf.clear();
    }

    raf.close();
  }

  public static void appendFile(String fileName) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(FileChannelDemo.class.getResource("/")
        .getPath() + fileName, "rw");
    // operation for append
    raf.seek(raf.length());

    FileChannel inChannel = raf.getChannel();

    CharBuffer buf = CharBuffer.wrap("\nHello");
    CharBuffer buf2 = CharBuffer.wrap(" world");
    ByteBuffer[] array = {StandardCharsets.UTF_8.encode(buf), StandardCharsets.UTF_8.encode(buf2)};
    inChannel.write(array);

    raf.close();
  }

  public static void mirrorFile(String originFile, String mirrorFile) throws IOException {
    RandomAccessFile fromFile = new RandomAccessFile(FileChannelDemo.class.getResource("/")
        .getPath() + originFile, "r");
    RandomAccessFile toFile = new RandomAccessFile(FileChannelDemo.class.getResource("/")
        .getPath() + mirrorFile, "rw");

    FileChannel fromChannel = fromFile.getChannel(), toChannel = toFile.getChannel();
    toChannel.transferFrom(fromChannel, 0, fromChannel.size());
  }

  public static void main(String[] args) {
    try {
//      readFile("lesson.txt");
//      repeatFile("lesson.txt");
//      appendFile("lesson.txt");
      mirrorFile("lesson.txt", "mirror.txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
