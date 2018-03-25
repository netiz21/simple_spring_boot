package com.thanos.springboot.common;

/**
 * @author solarknight created on 16/12/6 上午11:57
 * @version 1.0
 */
public enum ResponseEnum {

  SUCCESS(0, ""),
  INVALID_PARAM(1001, "Invalid param"),
  REQUEST_EXCEED(1002, "Request count exceed"),
  INTERNAL_ERROR(1111, "Internal error");

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
