package com.thanos.springboot.vo;

import com.thanos.springboot.bo.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author solarknight created on 17/3/20 下午9:52
 * @version 1.0
 */
@Data
public class UserCreateReqVo {

  @NotNull
  @Size(min = 2, max = 20)
  private String name;

  @NotNull
  private String sex;

  @NotNull
  @Size(min = 2, max = 50)
  private String descp;

  public static class Converter {
    public static User toUser(UserCreateReqVo reqVo) {
      User user = new User();
      user.setName(reqVo.getName());
      user.setSex(reqVo.getSex());
      user.setDescp(reqVo.getDescp());
      return user;
    }
  }
}
