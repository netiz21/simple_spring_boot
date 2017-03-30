package com.thanos.springboot.service.impl;

import com.thanos.springboot.bo.Operation;
import com.thanos.springboot.dao.OperationMapper;
import com.thanos.springboot.service.IOperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author solarknight created on 17/3/20 下午7:06
 * @version 1.0
 */
@Service
public class OperationService implements IOperationService {

  @Autowired
  private OperationMapper operationMapper;

  @Override
  public Operation findById(Long id) {
    if (id == null) {
      return null;
    }
    return operationMapper.selectByPrimaryKey(id);
  }

  @Override
  public void recordOperation(Operation operation) {
    if (operation == null) {
      return;
    }

    if (operation.getCreateTime() == null) {
      operation.setCreateTime(new Date());
    }

    operationMapper.insertSelective(operation);
  }
}