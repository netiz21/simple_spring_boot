package com.thanos.springboot.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author solarknight created on 2016/11/20 0:33
 * @version 1.0
 */
@Configuration
@PropertySource(value = "classpath:message.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("msg")
public class MessageResource {

  private String name;
  private String content;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
