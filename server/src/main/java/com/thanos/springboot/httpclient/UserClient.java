package com.thanos.springboot.httpclient;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.StandardResponse;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author peiheng.zph created on 18/3/1 下午4:53
 * @version 1.0
 */
@FeignClient(name = "user", url = "http://127.0.0.1:8080/ssb")
public interface UserClient {

  @GetMapping("/user/list")
  StandardResponse<List<User>> queryUsers();

  @GetMapping("/user/{id}")
  StandardResponse<User> queryUserById(@PathVariable("id") long id);
}
