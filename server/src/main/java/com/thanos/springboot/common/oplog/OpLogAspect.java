package com.thanos.springboot.common.oplog;

import com.alibaba.fastjson.JSON;
import com.thanos.springboot.bo.Operation;
import com.thanos.springboot.service.IOperationService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author solarknight created on 17/3/20 下午3:44
 * @version 1.0
 */
@Slf4j
@Aspect
@Configurable
public class OpLogAspect {

  private static final int MAX_STR_LENGTH = 200;

  @Autowired
  private IOperationService operationService;

  @Pointcut("@annotation(com.thanos.springboot.common.oplog.OpLog)")
  public void logPoint() {
  }

  @Around("logPoint()")
  public Object logProcess(ProceedingJoinPoint pjp) throws Throwable {
    if (SecLogContext.checkLogContext()) {
      log.info("Find existed log context, skip process");
      return pjp.proceed();
    }

    SecLogContext.startLogContext();
    log.info("Start log process now");

    try {
      Object result = pjp.proceed();
      recordOnReturn(pjp, result);
      return result;

    } catch (Throwable t) {
      recordOnThrow(pjp, t);
      throw t;

    } finally {
      SecLogContext.destroyLogContext();
    }
  }

  private void recordOnReturn(ProceedingJoinPoint pjp, Object result) {
    recordSilently(pjp, result, null);
  }

  private void recordOnThrow(ProceedingJoinPoint pjp, Throwable t) {
    recordSilently(pjp, null, t);
  }

  private void recordSilently(ProceedingJoinPoint pjp, Object result, Throwable throwable) {
    try {
      doRecord(pjp, result, throwable);
    } catch (Exception e) {
      log.warn("Caught exception when try to record log", e);
    }
  }

  private void doRecord(ProceedingJoinPoint pjp, Object result, Throwable t) {
    // get original class name in case proxy
    String classStr = pjp.getTarget().getClass().getSimpleName();
    classStr = classStr.contains("$") ? classStr.substring(0, classStr.indexOf("$")) : classStr;

    String methodStr = pjp.getSignature().getName();
    String paramStr = getParamStr(pjp.getArgs());
    String resultStr = getResultStr(result);
    String throwableStr = t == null ? "" : t.getClass().getSimpleName();

    Operation operation = new Operation();
    operation.setTargetClass(classStr);
    operation.setTargetMethod(methodStr);
    operation.setMethodParam(paramStr);
    operation.setMethodReturn(resultStr);
    operation.setMethodThrow(throwableStr);
    operationService.recordOperation(operation);
  }

  private String getParamStr(Object[] params) {
    String paramStr = JSON.toJSONString(params);
    return paramStr.length() > MAX_STR_LENGTH ? paramStr.substring(0, MAX_STR_LENGTH) : paramStr;
  }

  private String getResultStr(Object result) {
    String resultStr = result == null ? "" : JSON.toJSONString(result);
    return resultStr.length() > MAX_STR_LENGTH ? resultStr.substring(0, MAX_STR_LENGTH) : resultStr;
  }
}
