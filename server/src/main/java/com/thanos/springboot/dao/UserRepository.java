package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by solarknight on 2017/4/25.
 */
public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findBySex(@Param("sex") int sex);

  List<User> findByName(@Param("name") String name);
}
