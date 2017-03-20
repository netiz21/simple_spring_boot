package com.thanos.springboot.dao;

import com.thanos.springboot.bo.Operation;

public interface OperationMapper {

    int insert(Operation record);

    int insertSelective(Operation record);

    Operation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Operation record);

    int updateByPrimaryKey(Operation record);
}