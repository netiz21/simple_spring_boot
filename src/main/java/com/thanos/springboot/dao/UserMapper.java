package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author solarknight created on 2016/11/21 0:13
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    User findById(@Param("id") Long id);

    List<User> findAll();
}
