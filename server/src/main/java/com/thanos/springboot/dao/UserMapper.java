package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import java.util.List;

/**
 * @author solarknight created on 2016/11/21 0:13
 * @version 1.0
 */
public interface UserMapper {

  User findById(Long id);

  List<User> findAll();

  int insert(User record);
}
