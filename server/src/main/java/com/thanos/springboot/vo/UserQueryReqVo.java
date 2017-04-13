package com.thanos.springboot.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author solarknight created on 17/3/13 下午6:52
 * @version 1.0
 */
public class UserQueryReqVo {

  @ApiModelProperty("user id")
  @Min(1)
  private long id;

  @ApiModelProperty("user name")
  @Size(min = 2, max = 50)
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "UserQueryReqVo{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
