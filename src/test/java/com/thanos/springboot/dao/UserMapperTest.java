package com.thanos.springboot.dao;

import com.alibaba.fastjson.JSON;
import com.thanos.springboot.bo.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author peiheng.zph created on 16/11/22 下午4:35
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void findById() throws Exception {
    User user = userMapper.findById(1L);
    Assert.assertNotNull(user);
    System.err.println(JSON.toJSONString(user));
  }

}