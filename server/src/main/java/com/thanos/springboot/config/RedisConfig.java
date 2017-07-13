package com.thanos.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author peiheng.zph created on 17/7/12 下午8:47
 * @version 1.0
 */
@Configuration
public class RedisConfig {

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory);
    return template;
  }
}
