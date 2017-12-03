package com.thanos.springboot.config;

import com.thanos.springboot.dao.OperationDao;
import com.thanos.springboot.dao.UserDao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Autowired;
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
  public JdbiFactoryBean jdbiFactoryBean(@Autowired DataSource dataSource) {
    return new JdbiFactoryBean(dataSource);
  }

  @Bean
  public SqlObjectPlugin sqlObjectPlugin() {
    return new SqlObjectPlugin();
  }

  @Bean
  public UserDao userDao(@Autowired Jdbi jdbi) {
    return jdbi.onDemand(UserDao.class);
  }

  @Bean
  public OperationDao operationDao(@Autowired Jdbi jdbi) {
    return jdbi.onDemand(OperationDao.class);
  }
}
