package com.thanos.springboot.common.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author peiheng.zph created on 17/1/22 上午10:27
 * @version 1.0
 */
public class Inet4AddressDemo {

  public String getInet4Address() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    Inet4AddressDemo demo = new Inet4AddressDemo();
    System.out.println(demo.getInet4Address());
  }
}
