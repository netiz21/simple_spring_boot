package com.thanos.springboot.bo;

import java.util.Date;

import lombok.Data;

@Data
public class Operation {
  private Long id;

  private String targetClass;

  private String targetMethod;

  private String methodParam;

  private String methodReturn;

  private String methodThrow;

  private Date createTime;
}