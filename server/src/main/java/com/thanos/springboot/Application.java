package com.thanos.springboot;

import com.thanos.springboot.common.CustomApplicationListener;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@SpringBootApplication
@EnableSpringConfigured
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .sources(Application.class)
        .listeners(CustomApplicationListener.INSTANCE)
        .run(args);
  }
}
