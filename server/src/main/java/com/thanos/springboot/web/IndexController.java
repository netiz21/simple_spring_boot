package com.thanos.springboot.web;

import com.thanos.springboot.common.MessageResource;
import com.thanos.springboot.common.StandardResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {
  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

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
    logger.info("Request uri = {}", req.getRequestURI());
    logger.info("Request url = {}", req.getRequestURL());
    logger.info("Servlet path = {}", req.getServletPath());
    logger.info("Request context path = {}", req.getContextPath());
    logger.info("url + query string = {}", req.getRequestURL().toString() + "?" + req.getQueryString());
    return StandardResponse.ok();
  }

  @GetMapping("/error")
  public StandardResponse<Void> error(int input) {
    try {
      if (input < 10) {
        throw new IllegalArgumentException("input is less than 10!");
      }
    } catch (Exception e) {
      logger.error("Found exception!", e);
    }
    return StandardResponse.ok();
  }

}
