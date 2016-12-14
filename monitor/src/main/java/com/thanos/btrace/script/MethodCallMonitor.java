package com.thanos.btrace.script;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;
import com.sun.btrace.annotations.TargetInstance;
import com.sun.btrace.annotations.TargetMethodOrField;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/14 下午7:12
 * @version 1.0
 */
@BTrace
public class MethodCallMonitor {

  @OnMethod(clazz = "com.thanos.springboot.web.UserController",
      method = "/.*/",
      location = @Location(value = Kind.CALL, clazz = "/.+/", method = "/.+/"))
  public static void onMonitor(@ProbeClassName String clazz, @ProbeMethodName String method,
                               @Self Object self, @TargetInstance Object target,
                               @TargetMethodOrField Object targetMethod) {
    print("stack " + clazz);
    print("#" + method);
    println();
    print("  target " + target);
    print("#" + targetMethod);
    println();
  }

}
