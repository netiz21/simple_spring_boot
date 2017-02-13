package com.thanos.monitor.ext.logback.util;

/**
 * @author peiheng.zph created on 17/2/13 下午4:55
 * @version 1.0
 */
public class JsonConvertException extends RuntimeException {
  public JsonConvertException() {
  }

  public JsonConvertException(String message) {
    super(message);
  }

  public JsonConvertException(String message, Throwable cause) {
    super(message, cause);
  }

  public JsonConvertException(Throwable cause) {
    super(cause);
  }
}
