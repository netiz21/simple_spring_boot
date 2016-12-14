package com.thanos.btrace.script;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/14 下午2:21
 * @version 1.0
 */
@BTrace
public class MethodLineMonitor {

  @OnMethod(clazz = "com.thanos.springboot.web.UserController",
      method = "/.*/",
      location = @Location(value = Kind.LINE, line = -1))
  public static void onMonitor(@ProbeClassName String clazz, @ProbeMethodName String method,
                               int line) {
    print("stack " + clazz);
    print("#" + method);
    print(" : " + line);
    println();
  }
}
