package com.thanos.springboot.bo;

import java.util.Date;

/**
 * @author peiheng.zph created on 17/3/20 下午3:44
 * @version 1.0
 */
public class Operation {

  private Long id;
  /**
   * 日志记录对应的起始类
   */
  private String targetClass;
  /**
   * 日志记录对应的起始方法
   */
  private String targetMethod;
  /**
   * 方法入参
   */
  private String methodParam;
  /**
   * 方法返回值
   */
  private String methodReturn;
  /**
   * 方法抛出的异常类型
   */
  private String methodThrow;
  /**
   * 创建时间
   */
  private Date createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTargetClass() {
    return targetClass;
  }

  public void setTargetClass(String targetClass) {
    this.targetClass = targetClass;
  }

  public String getTargetMethod() {
    return targetMethod;
  }

  public void setTargetMethod(String targetMethod) {
    this.targetMethod = targetMethod;
  }

  public String getMethodParam() {
    return methodParam;
  }

  public void setMethodParam(String methodParam) {
    this.methodParam = methodParam;
  }

  public String getMethodReturn() {
    return methodReturn;
  }

  public void setMethodReturn(String methodReturn) {
    this.methodReturn = methodReturn;
  }

  public String getMethodThrow() {
    return methodThrow;
  }

  public void setMethodThrow(String methodThrow) {
    this.methodThrow = methodThrow;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
