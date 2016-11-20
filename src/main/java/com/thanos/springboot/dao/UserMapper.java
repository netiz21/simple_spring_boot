package com.thanos.springboot.dao;

import com.thanos.springboot.bo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author solarknight created on 2016/11/21 0:13
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);
}
