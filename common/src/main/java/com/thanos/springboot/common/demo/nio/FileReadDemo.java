package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author peiheng.zph created on 17/5/16 下午7:32
 * @version 1.0
 */
public class FileReadDemo {

  public static void readFile(String fileName) throws IOException {
    RandomAccessFile aFile = new RandomAccessFile(FileReadDemo.class.getClassLoader().
        getResource(fileName).getFile(), "r");
    FileChannel inChannel = aFile.getChannel();

    ByteBuffer buf = ByteBuffer.allocate(48);

    while (inChannel.read(buf) != -1) {
      buf.flip();

      while (buf.hasRemaining()) {
        System.out.print((char) buf.get());
      }

      buf.clear();
    }
    aFile.close();
  }

  public static void main(String[] args) {
    try {
      readFile("lesson.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
