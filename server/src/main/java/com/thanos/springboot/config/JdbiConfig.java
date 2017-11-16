package com.thanos.springboot.config;

import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author peiheng.zph created on 17/11/16 下午7:54
 * @version 1.0
 */
@Configuration
public class JdbiConfig {

  @Bean
  public Jdbi jdbi(DataSource dataSource) {
    Jdbi jdbi = Jdbi.create(dataSource);
    return jdbi;
  }
}
