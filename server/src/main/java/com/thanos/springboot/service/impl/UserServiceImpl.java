package com.thanos.springboot.service.impl;

import com.thanos.springboot.bo.User;
import com.thanos.springboot.common.oplog.OpLog;
import com.thanos.springboot.dao.UserDao;
import com.thanos.springboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author solarknight created on 16/12/10 上午11:20
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public User findById(long id) {
    return userDao.findById(id);
  }

  @Override
  public List<User> findAll() {
    return userDao.findAll();
  }

  @OpLog
  @Override
  public boolean createUser(User user) {
    if (user == null) {
      return false;
    }
    return userDao.insert(user.getName(), user.getSex(), user.getDescp()) > 0;
  }
}
