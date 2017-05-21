package com.thanos.springboot.common.demo.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by solarknight on 2017/5/21.
 */
public class FilesDemo {

  public static void filesUsage() {
    filesExists();
    filesCopy();
    filesOverwriting();
    filesMove();
    filesDelete();
    filesIterate();
  }

  private static void filesIterate() {
    Path rootPath = Paths.get(".");

    try {
      Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          System.out.println("visit file: " + file);
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void filesDelete() {
    Path path = Paths.get(".", "pom.xml.2.bak");
    try {
      Files.delete(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void filesMove() {
    Path path = Paths.get(".", "pom.xml.bak");
    Path path2 = Paths.get(".", "pom.xml.2.bak");
    try {
      Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void filesCopy() {
    Path path = Paths.get(".", "pom.xml");
    Path path2 = Paths.get(".", "pom.xml.bak");
    try {
      Files.copy(path, path2);
    } catch (FileAlreadyExistsException e) {
      System.out.println("Target file already exists");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void filesOverwriting() {
    Path path = Paths.get(".", "pom.xml");
    Path path2 = Paths.get(".", "pom.xml.bak");
    try {
      Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void filesExists() {
    Path path = Paths.get(".", "pom.xml");
    System.out.println(path.toAbsolutePath());

    boolean pathExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    System.out.println(pathExists);
  }

  public static void main(String[] args) {
    filesUsage();
  }
}
