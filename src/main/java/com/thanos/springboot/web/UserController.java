package com.thanos.springboot.web;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.StandardResponse;
import com.thanos.springboot.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author peiheng.zph created on 16/12/10 上午11:23
 * @version 1.0
 */
@RestController
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private IUserService userService;

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public StandardResponse<User> user(@PathVariable long id) {
    User user = userService.findById(id);
    return StandardResponse.ok(user);
  }

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public StandardResponse<List<User>> users() {
    List<User> users = userService.findAll();
    return StandardResponse.ok(users);
  }
}
