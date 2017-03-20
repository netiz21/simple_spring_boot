package com.thanos.springboot.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class Operation {
  private Long id;

  private String targetClass;

  private String targetMethod;

  private String methodParam;

  private String methodReturn;

  private String methodThrow;

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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  public static class OperationBuilder {
    public static Operation build(String classStr, String methodStr, String paramStr, String resultStr, String throwableStr) {
      Operation operation = new Operation();
      operation.setTargetClass(classStr);
      operation.setTargetMethod(methodStr);
      operation.setMethodParam(paramStr);
      operation.setMethodReturn(resultStr);
      operation.setMethodThrow(throwableStr);
      operation.setCreateTime(new Date());
      return operation;
    }
  }
}