package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author peiheng.zph created on 17/5/16 下午7:32
 * @version 1.0
 */
public class FileChannelDemo {

  public static void readFile(String fileName) throws IOException {
    RandomAccessFile aFile = new RandomAccessFile(FileChannelDemo.class.getClassLoader().
        getResource(fileName).getFile(), "r");
    FileChannel inChannel = aFile.getChannel();

    ByteBuffer buf = ByteBuffer.allocate(256);

    while (inChannel.read(buf) != -1) {
      buf.flip();

      CharBuffer tmp = StandardCharsets.UTF_8.decode(buf);
      while (tmp.hasRemaining()) {
        System.out.print(tmp.get());
      }

      buf.clear();
    }

    aFile.close();
  }

  public static void writeFile(String fileName) throws IOException {
    RandomAccessFile aFile = new RandomAccessFile(FileChannelDemo.class.getClassLoader().
        getResource(fileName).getFile(), "rw");
    FileChannel inChannel = aFile.getChannel();

    CharBuffer buf = CharBuffer.wrap("Hello world");
    inChannel.write(StandardCharsets.UTF_8.encode(buf));

    aFile.close();
  }

  public static void main(String[] args) {
    try {
      readFile("lesson.txt");
      writeFile("lesson.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
