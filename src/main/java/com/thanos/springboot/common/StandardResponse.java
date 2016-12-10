package com.thanos.springboot.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author peiheng.zph created on 16/12/5 下午11:20
 * @version 1.0
 */
public class StandardResponse<T> {

  private int status;
  private String errorMsg;
  private T data;

  public static <T> StandardResponse<T> error(ResponseEnum responseEnum) {
    StandardResponse<T> response = new StandardResponse<>();
    response.setStatus(responseEnum.status());
    response.setErrorMsg(responseEnum.msg());
    return response;
  }

  public static <T> StandardResponse<T> ok(T data) {
    StandardResponse<T> response = new StandardResponse<>();
    response.setStatus(ResponseEnum.SUCCESS.status());
    response.setData(data);
    return response;
  }

  public static <T> StandardResponse<T> ok() {
    StandardResponse<T> response = new StandardResponse<>();
    response.setStatus(ResponseEnum.SUCCESS.status());
    return response;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
