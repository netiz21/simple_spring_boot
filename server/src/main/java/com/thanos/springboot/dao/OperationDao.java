package com.thanos.springboot.dao;

import com.thanos.springboot.bo.Operation;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * @author solarknight created on 2016/11/21 10:13
 * @version 1.0
 */
@RegisterBeanMapper(Operation.class)
public interface OperationDao {

  @SqlUpdate("insert into operation (target_class, target_method, method_param, method_return, method_throw, create_time) values " +
      "(:targetClass, :targetMethod, :methodParam, :methodReturn, :methodThrow, :createTime)")
  long insert(@BindBean Operation record);

  @SqlQuery("select * from operation where id = ?")
  Operation findById(long id);
}