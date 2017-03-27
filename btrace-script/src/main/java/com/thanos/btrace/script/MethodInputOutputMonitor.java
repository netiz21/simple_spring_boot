package com.thanos.btrace.script;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.timeMillis;

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

}
