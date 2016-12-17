package com.thanos.btrace.script;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Duration;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Where;

import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/10 下午12:16
 * @version 1.0
 */
@BTrace
public class WebServiceMonitor {

  @OnMethod(clazz = "@org.springframework.web.bind.annotation.RestController",
      method="/.+/",
      location = @Location(value = Kind.RETURN, where = Where.BEFORE))
  public static void onMethod(@ProbeClassName String probClass, @ProbeMethodName String probMethod,
                              @Duration long consume, @Return Object result) {
    print("invoke " + probClass);
    print("#" + probMethod);
    print(" return " + result);
    print(" consume " + consume / 1000000 + " ms");
    println();
  }
}
