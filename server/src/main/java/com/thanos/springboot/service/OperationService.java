package com.thanos.springboot.service;

import com.thanos.springboot.bo.Operation;

/**
 * @author solarknight created on 17/3/20 下午7:05
 * @version 1.0
 */
public interface OperationService {

  Operation findById(Long id);

  void recordOperation(Operation operation);
}
