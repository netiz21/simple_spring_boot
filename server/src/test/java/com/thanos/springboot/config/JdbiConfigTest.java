package com.thanos.springboot.config;

import com.google.common.base.Joiner;

import com.thanos.springboot.bo.User;

import org.assertj.core.util.Lists;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author peiheng.zph created on 17/11/16 下午7:56
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbiConfigTest {

  private Jdbi jdbi;
  private Handle handle;

  @Before
  public void setUpData() {
    jdbi = Jdbi.create("jdbc:h2:mem:test"); // (H2 in-memory database)
    handle = jdbi.open();

    handle.execute("CREATE TABLE user (id INTEGER PRIMARY KEY, name VARCHAR)");

    // Inline positional parameters
    handle.execute("INSERT INTO user(id, name) VALUES (?, ?)", 0, "Alice");

    // Positional parameters
    handle.createUpdate("INSERT INTO user(id, name) VALUES (?, ?)")
        .bind(0, 1) // 0-based parameter indexes
        .bind(1, "Bob")
        .execute();

    // Named parameters
    handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
        .bind("id", 2)
        .bind("name", "Clarice")
        .execute();

    // Named parameters from bean properties
    handle.createUpdate("INSERT INTO user(id, name) VALUES (:id, :name)")
        .bindBean(buildUser(3, "David"))
        .execute();

  }

  @Test
  public void testQuery1() {

    // Easy mapping to any type
    List<User> users = handle.createQuery("SELECT * FROM user ORDER BY name")
        .mapToBean(User.class)
        .list();

    Assert.assertTrue(!CollectionUtils.isEmpty(users));
    Assert.assertTrue(users.contains(buildUser(0, "Alice")));
    Assert.assertTrue(users.contains(buildUser(1, "Bob")));
    Assert.assertTrue(users.contains(buildUser(2, "Clarice")));
    Assert.assertTrue(users.contains(buildUser(3, "David")));
  }

  @Test
  public void testQuery2() {
    Optional<String> result = handle.select("select name from user where id = ?", 3)
        .mapTo(String.class)
        .findFirst();
    Assert.assertTrue(result.isPresent());
  }

  private User buildUser(long id, String name) {
    User user = new User();
    user.setId(id);
    user.setName(name);
    return user;
  }

  @Test
  public void testQuery3() {
    List<Long> list = Lists.newArrayList(1L, 2L, 3L);
    List<Map<String, Object>> result = handle.createQuery("select * from user where id in (<ids>)")
        .bindList("ids", list)
        .mapToMap()
        .list();

    assertThat(result).isNotNull();
    then(result.size()).isEqualTo(3);
  }

  @After
  public void clean() {
    handle.close();
  }

}
