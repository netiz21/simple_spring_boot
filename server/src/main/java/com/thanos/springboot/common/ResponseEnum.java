package com.thanos.springboot.common;

/**
 * @author peiheng.zph created on 16/12/6 上午11:57
 * @version 1.0
 */
public enum ResponseEnum {

  SUCCESS(0, ""), INVALID_PARAM(1001, "参数错误");

  ResponseEnum(int status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  private int status;
  private String msg;

  public int status() {
    return status;
  }

  public String msg() {
    return msg;
  }
}