package com.thanos.btrace.script;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

import java.util.List;
import java.util.Map;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.printArray;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.timeMillis;
import static com.sun.btrace.BTraceUtils.timestamp;

/**
 * @author peiheng.zph created on 16/12/19 上午11:35
 * @version 1.0
 */
@BTrace
public class MethodInputOutputMonitor {

  @OnMethod(clazz = "com.thanos.springboot.web.UserController",
      method = "getValues", location = @Location(Kind.RETURN))
  public static void onMonitor(@ProbeClassName String probClass, @ProbeMethodName String probMethod,
                               @Return AnyType result) {
    print(timeMillis());
    print("invoke " + probClass);
    print("#" + probMethod);
    print("  return " + str(result));
    println();
  }

  @OnMethod(clazz = "org.springframework.jdbc.core.JdbcTemplate",
      method = "update", location = @Location(Kind.RETURN))
  public static void onMonitor(@ProbeClassName String probClass, @ProbeMethodName String probMethod,
                               String sql, Object... args) {
    timestamp();
    print("invoke " + probClass);
    print("#" + probMethod);
    println();
    print(" sql " + sql + " args ");
    printArray(args);
    println();
  }

  @OnMethod(clazz = "com.sm.lawson.sink.db.conditionalload.load.InsertSqlLoader",
      method = "assertKeys", location = @Location(Kind.RETURN))
  public static void onMonitor2(@ProbeClassName String probClass, @ProbeMethodName String probMethod,
                               List<Map<String, Object>> entities) {
    timestamp();
    print("invoke " + probClass);
    print("#" + probMethod);
    println();
    for (Map<String, Object> map : entities) {
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        println(" key " + entry.getKey() + " value " + str(entry.getValue()));
      }
    }
    println();
    println();
  }

  @OnMethod(clazz = "com.hiriver.unbiz.mysql.lib.output.BinlogColumnValue",
      method = "<init>", location = @Location(Kind.RETURN))
  public static void onMonitor3(@Self Object self) {
    timestamp();
    print(" object " + self.toString());
    println();
  }
}
