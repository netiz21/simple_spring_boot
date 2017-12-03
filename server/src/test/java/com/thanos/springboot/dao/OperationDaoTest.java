package com.thanos.springboot.dao;

import com.thanos.springboot.bo.Operation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

/**
 * @author solarknight created on 2017/12/3 21:57
 * @version 1.0
 */
public class OperationDaoTest extends DALTest {

  @Autowired
  private OperationDao operationDao;

  @Test
  public void insert() throws Exception {
  }

  @Test
  public void findById() throws Exception {
    Operation operation = operationDao.findById(1L);
    assertNotNull(operation);
    then(operation.getTargetClass()).isEqualTo("Object");
  }

}