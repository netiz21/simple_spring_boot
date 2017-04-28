package com.thanos.springboot.service.impl;

import com.google.common.collect.Lists;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.oplog.OpLog;
import com.thanos.springboot.dao.UserRepository;
import com.thanos.springboot.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author solarknight created on 16/12/10 上午11:20
 * @version 1.0
 */
@Service
public class UserService implements IUserService {

//  @Autowired
//  private UserMapper userMapper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findById(long id) {
    return userRepository.findOne(id);
  }

  @Override
  public List<User> findAll() {
    return Lists.newArrayList(userRepository.findAll());
  }

  @OpLog
  @Override
  public User createUser(User user) {
    if (user == null) {
      return null;
    }
    return userRepository.save(user);
  }
}
