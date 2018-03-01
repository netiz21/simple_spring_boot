package com.thanos.springboot.web;

import com.thanos.springboot.common.MessageResource;
import com.thanos.springboot.common.StandardResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {

  @Autowired
  private MessageResource messageResource;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @GetMapping("/hello")
  public String index() {
    if (StringUtils.isEmpty(stringRedisTemplate.opsForValue().get("hello"))) {
      stringRedisTemplate.opsForValue().set("hello", "world", 5, TimeUnit.SECONDS);
    }
    return "Hello, world!";
  }

  @GetMapping("/message")
  public String message(String name) {
    if (Objects.equals(messageResource.getName(), name)) {
      return messageResource.getContent();
    }
    return "Found no message by name " + name;
  }

  @GetMapping("/reload")
  public String reload() {
    return "Reload success";
  }

  @GetMapping("/standard")
  public StandardResponse<Void> standard(HttpServletRequest req) {
    log.info("Request uri = {}", req.getRequestURI());
    log.info("Request url = {}", req.getRequestURL());
    log.info("Servlet path = {}", req.getServletPath());
    log.info("Request context path = {}", req.getContextPath());
    log.info("url + query string = {}", req.getRequestURL().toString() + "?" + req.getQueryString());
    return StandardResponse.ok();
  }

  @GetMapping("/error")
  public StandardResponse<Void> error(int input) {
    try {
      if (input < 10) {
        throw new IllegalArgumentException("input is less than 10!");
      }
    } catch (Exception e) {
      log.error("Found exception!", e);
    }
    return StandardResponse.ok();
  }

}
