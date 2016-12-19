package com.thanos.btrace.script;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

/**
 * @author peiheng.zph created on 16/12/19 上午11:35
 * @version 1.0
 */
@BTrace
public class MethodInputOutputMonitor {

  @OnMethod(clazz = "com.thanos.springboot.service.impl.UserService",
      method = "findById", location = @Location(Kind.RETURN))
  public static void onMonitor(@Self Object o, long id, @Return AnyType result) {
    BTraceUtils.print("target " + o);
    BTraceUtils.print("  args ");
    BTraceUtils.print(id);
    BTraceUtils.print("  return ");
    BTraceUtils.printFields(result);
  }

}
