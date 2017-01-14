package com.thanos.btrace.script;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;

import static com.sun.btrace.BTraceUtils.println;

/**
 * @author peiheng.zph created on 16/12/22 下午3:45
 * @version 1.0
 */
@BTrace
public class ClassLoadMonitor {
  @OnMethod(
      clazz="+java.lang.ClassLoader",
      method="defineClass",
      location=@Location(Kind.RETURN)
  )
  public static void defineclass(@Return Class cl) {
    println("loaded " +  BTraceUtils.Reflective.name(cl));
    println("==========================");
  }
}
