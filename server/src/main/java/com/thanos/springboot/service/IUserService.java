package com.thanos.springboot.service;

import com.thanos.springboot.bo.User;

import java.util.List;

/**
 * @author solarknight created on 16/12/10 上午11:21
 * @version 1.0
 */
public interface IUserService {

  User findById(long id);

  List<User> findAll();

  User createUser(User user);
}
