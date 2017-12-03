package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * @author solarknight created on 2016/11/21 10:13
 * @version 1.0
 */
@RegisterBeanMapper(User.class)
public interface UserDao {

  @SqlUpdate("insert into user (name, sex, descp) values (:name, :sex, :descp)")
  @GetGeneratedKeys
  long insert(@Bind("name") String name, @Bind("sex") String sex, @Bind("descp") String descp);

  @SqlQuery("select id, name, sex, descp from user where id = ?")
  User findById(long id);

  @SqlQuery("select id, name, sex, descp from user")
  List<User> findAll();
}
