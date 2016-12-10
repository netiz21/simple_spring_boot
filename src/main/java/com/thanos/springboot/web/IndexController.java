package com.thanos.springboot.web;

import com.thanos.springboot.common.MessageResource;
import com.thanos.springboot.common.StandardResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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

  @RequestMapping("/hello")
  public String index() {
    return "Hello, world!";
  }

  @RequestMapping("/message")
  public String message(String name) {
    if (Objects.equals(messageResource.getName(), name)) {
      return messageResource.getContent();
    }
    return "Found no message by name " + name;
  }

  @RequestMapping("/reload")
  public String reload() {
    return "Reload success";
  }

  @RequestMapping("/standard")
  public StandardResponse<Void> standard(HttpServletRequest req) {
    logger.info("Request uri = {}", req.getRequestURI());
    logger.info("Request url = {}", req.getRequestURL());
    logger.info("Request context path = {}", req.getContextPath());
    logger.info("url + query string = {}", req.getRequestURL().toString() + "?" + req.getQueryString());
    return StandardResponse.ok();
  }
}
