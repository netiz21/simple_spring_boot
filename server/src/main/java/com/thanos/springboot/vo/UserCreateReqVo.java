package com.thanos.springboot.vo;

import com.thanos.springboot.bo.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author solarknight created on 17/3/20 下午9:52
 * @version 1.0
 */
public class UserCreateReqVo {

  @NotNull
  @Size(min = 2, max = 20)
  private String name;

  @NotNull
  @Min(0)
  @Max(1)
  private Integer sex;

  @NotNull
  @Size(min = 2, max = 50)
  private String descp;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public String getDescp() {
    return descp;
  }

  public void setDescp(String descp) {
    this.descp = descp;
  }

  public static class UserCreateReqVoConverter {
    public static User convert(UserCreateReqVo reqVo) {
      User user = new User();
      user.setName(reqVo.getName());
      user.setSex(reqVo.getSex());
      user.setDescp(reqVo.getDescp());
      return user;
    }
  }
}
