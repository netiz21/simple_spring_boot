package com.thanos.springboot.common.demo.security;

import org.junit.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

/**
 * @author peiheng.zph created on 18/3/5 下午4:29
 * @version 1.0
 */
public class MessageDigestDemo {

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String content = "Hello world";

//    printAvailableServices();

    MessageDigest md = MessageDigest.getInstance("MD5");
    // 128 bit
    Assert.assertTrue(md.digest(content.getBytes()).length == 16);

    md = MessageDigest.getInstance("SHA-1");
    // 160 bit
    Assert.assertTrue(md.digest(content.getBytes()).length == 20);

    md = MessageDigest.getInstance("SHA-256");
    // 256 bit
    Assert.assertTrue(md.digest(content.getBytes()).length == 32);
  }

  private static void printAvailableServices() {
    Provider[] providers = Security.getProviders();
    Arrays.stream(providers)
        .forEach(it -> System.out.println(it.getServices()));
  }
}
