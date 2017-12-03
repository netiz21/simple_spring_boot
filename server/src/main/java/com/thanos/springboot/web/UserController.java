package com.thanos.springboot.web;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.ResponseEnum;
import com.thanos.springboot.common.StandardResponse;
import com.thanos.springboot.service.IUserService;
import com.thanos.springboot.vo.UserCreateReqVo;
import com.thanos.springboot.vo.UserQueryReqVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author solarknight created on 16/12/10 上午11:23
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@Api(value = "user controller", description = "CRUD operations about user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private IUserService userService;

  @ApiOperation(value = "View user by id")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public StandardResponse<User> user(@PathVariable long id) {
    User user = userService.findById(id);
    return StandardResponse.ok(user);
  }

  @ApiOperation(value = "View user by id and other condition")
  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public StandardResponse<User> getUser(@Valid @ModelAttribute UserQueryReqVo reqVo) {
    logger.info("Query param {}", reqVo);
    User user = userService.findById(reqVo.getId());
    return StandardResponse.ok(user);
  }

  @ApiOperation("Get all users")
  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public StandardResponse<List<User>> users() {
    long begin = System.currentTimeMillis();
    List<User> users = userService.findAll();
    logger.info("invoke UserController#users success, time consume = {}",
        System.currentTimeMillis() - begin);
    return StandardResponse.ok(users);
  }

  @ApiOperation("Add new user")
  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  public StandardResponse<Void> createUser(@Valid @RequestBody UserCreateReqVo reqVo) {
    User user = UserCreateReqVo.Converter.toUser(reqVo);
    boolean success = userService.createUser(user);
    if (success) {
      return StandardResponse.ok();
    }
    return StandardResponse.error(ResponseEnum.INTERNAL_ERROR);
  }
}
