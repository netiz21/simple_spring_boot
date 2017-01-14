package com.thanos.btrace.script;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;
import com.sun.btrace.annotations.Where;

import static com.sun.btrace.BTraceUtils.jstack;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/15 上午10:15
 * @version 1.0
 */
@BTrace
public class MethodCallMonitor {

  @OnMethod(clazz = "com.thanos.springboot.web.UserController",
      method = "/.*/",
      location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/", where = Where.AFTER))
  public static void onMonitor(@ProbeClassName String probClass, @TargetInstance Object instance,
                               @TargetMethodOrField String method, @Duration long duration) {
    print("invoke " + probClass);
    jstack();
    print(" " + instance + "#" + method);
    print(" cost " + duration / 1000000 + "ms");
    println();
  }
}
