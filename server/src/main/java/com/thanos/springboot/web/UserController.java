package com.thanos.springboot.web;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.StandardResponse;
import com.thanos.springboot.service.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/user/{id}")
  public StandardResponse<User> user(@PathVariable long id) {
    User user = userService.findById(id);
    return StandardResponse.ok(user);
  }

  @GetMapping("/users")
  public StandardResponse<List<User>> users() {
    long begin = System.currentTimeMillis();
    List<User> users = userService.findAll();
    logger.info("invoke UserController#users success, time consume = {}",
        System.currentTimeMillis() - begin);
    return StandardResponse.ok(users);
  }
}
