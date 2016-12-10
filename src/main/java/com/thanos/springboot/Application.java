package com.thanos.springboot;

import com.thanos.springboot.common.CustomApplicationListener;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author solarknight created on 2016/11/20 0:16
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.thanos.springboot.dao")
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.addListeners(CustomApplicationListener.INSTANCE);
    app.run(args);
  }
}
