package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author solarknight created on 2017/12/3 20:23
 * @version 1.0
 */
public class UserDaoTest extends DALTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void testInsert() {
    long id = userDao.insert("Nakita", "female", "heroine");
    assertThat(id).isEqualTo(3L);
  }

  @Test
  public void testFindById() {
    User user = userDao.findById(1L);
    assertThat(user).isNotNull();
    then(user.getName()).isEqualTo("Tom");
    then(user.getSex()).isEqualTo("male");
    then(user.getDescp()).isEqualTo("Tom Bruce");
  }

  @Test
  public void testFindAll() {
    List<User> users = userDao.findAll();
    assertThat(users).isNotNull();
    then(users.size()).isEqualTo(2);
  }
}