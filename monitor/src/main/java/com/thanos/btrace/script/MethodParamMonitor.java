package com.thanos.btrace.script;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Self;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.printArray;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/15 上午10:15
 * @version 1.0
 */
@BTrace
public class MethodParamMonitor {

  @OnMethod(clazz = "com.thanos.springboot.web.UserController",
      method = "/.*/",
      location=@Location(value=Kind.CALL, clazz="/.*/", method="/.*/"))
  public static void onMonitor(@Self Object self, @ProbeMethodName String pmn, AnyType[] args) { // all calls to methods
    // self - this for the method call
    // pmn - textual representation of the method
    // contents of args array:
    // [0]..[n] - original method call arguments
    print("invoke method " + pmn);
    print(" with args ");
    printArray(args);
    println();
  }
}
