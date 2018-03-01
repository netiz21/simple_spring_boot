package com.thanos.springboot.web;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.ResponseEnum;
import com.thanos.springboot.common.StandardResponse;
import com.thanos.springboot.httpclient.UserClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author peiheng.zph created on 18/3/1 下午4:47
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignController {

  @Autowired
  private UserClient userClient;

  @ApiOperation(value = "View user by id")
  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public StandardResponse<User> user(@PathVariable long id) {
    StandardResponse<User> response = userClient.queryUserById(id);
    if (response.getStatus() != ResponseEnum.SUCCESS.status()) {
      return StandardResponse.error(ResponseEnum.INTERNAL_ERROR);
    }

    log.info("Query user info from remote, {}", response.getData());
    return StandardResponse.ok(response.getData());
  }

}
