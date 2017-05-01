package com.thanos.springboot.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * @author peiheng.zph created on 17/1/22 下午5:30
 * @version 1.0
 */
public class CustomJavaAgent {
  private static final Logger logger = LoggerFactory.getLogger(CustomJavaAgent.class);

  private static volatile Instrumentation instrumentation;

  /**
   * JVM hook to statically load the javaagent at startup.
   * <p>
   * After the Java Virtual Machine (JVM) has initialized, the premain method
   * will be called. Then the real application main method will be called.
   */
  public static void premain(String args, Instrumentation inst) throws Exception {
    new AgentBuilder.Default()
        .type(ElementMatchers.nameEndsWith("Timed"))
        .transform((builder, type, classLoader, module) ->
            builder.method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TimingInterceptor.class))
        ).installOn(inst);
    instrumentation = inst;
  }

  /**
   * JVM hook to dynamically load javaagent at runtime.
   * <p>
   * The agent class may have an agentmain method for use when the agent is
   * started after VM startup.
   */
  public static void agentmain(String args, Instrumentation inst) throws Exception {
    logger.info("agentmain method invoked with args: {} and inst: {}", args, inst);
    instrumentation = inst;
    instrumentation.addTransformer(new CustomClassFileTransformer());
  }

  /**
   * Programmatic hook to dynamically load javaagent at runtime.
   */
  public static void initialize() {
    if (instrumentation == null) {
      CustomJavaAgentLoader.loadAgent();
    }
  }

  public static long sizeOf(Object object) {
    return instrumentation.getObjectSize(object);
  }
}
