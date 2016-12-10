package com.thanos.btrace.script;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/10 下午12:16
 * @version 1.0
 */
@BTrace
public class ControllerMethod {

  @OnMethod(clazz = "/com\\.thanos\\.springboot\\.controller\\..*", method = "/.*/")
  public static void onMethod(@Self Object o, @ProbeClassName String probClass,
                              @ProbeMethodName String probMethod) {
    println("this = " + o);
    print("entered " + probClass);
    println("." + probMethod);
  }
}
