package com.thanos.springboot.service.impl;

import com.thanos.springboot.bo.Operation;
import com.thanos.springboot.dao.OperationDao;
import com.thanos.springboot.service.OperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author solarknight created on 17/3/20 下午7:06
 * @version 1.0
 */
@Service
public class OperationServiceImpl implements OperationService {

  @Autowired
  private OperationDao operationDao;

  @Override
  public Operation findById(Long id) {
    if (id == null) {
      return null;
    }
    return operationDao.findById(id);
  }

  @Override
  public void recordOperation(Operation operation) {
    if (operation == null) {
      return;
    }

    if (operation.getCreateTime() == null) {
      operation.setCreateTime(new Date());
    }

    operationDao.insert(operation);
  }
}
