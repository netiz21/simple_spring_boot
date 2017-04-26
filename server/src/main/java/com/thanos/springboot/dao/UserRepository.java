package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by solarknight on 2017/4/25.
 */
@RepositoryRestResource(path = "user")
public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findBySex(@Param("sex") int sex);
}
