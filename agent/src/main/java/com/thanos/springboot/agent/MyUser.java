package com.thanos.springboot.agent;

/**
 * @author peiheng.zph created on 17/1/24 上午10:19
 * @version 1.0
 */
public class MyUser {

  // test object reference size
  // when used Compressed Pointer, the size is 4 bytes
  private Object obj = new Object();
//  private byte j;

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }

  public String getName() {
    return "foo";
  }
}
