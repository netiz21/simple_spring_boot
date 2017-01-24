package com.thanos.springboot.agent;

import com.sun.tools.attach.VirtualMachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * @author peiheng.zph created on 17/1/22 下午7:55
 * @version 1.0
 */
public class CustomJavaAgentLoader {

  static final Logger logger = LoggerFactory.getLogger(CustomJavaAgentLoader.class);

  private static final String jarFilePath = "/Users/zph/.m2/repository/" +
      "com/thanos/web/agent/1.0.0-SNAPSHOT/agent-1.0.0-SNAPSHOT-jar-with-dependencies.jar";

  public static void loadAgent() {
    logger.info("dynamically loading java agent");
    String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
    int p = nameOfRunningVM.indexOf('@');
    String pid = nameOfRunningVM.substring(0, p);

    try {
      VirtualMachine vm = VirtualMachine.attach(pid);
      vm.loadAgent(jarFilePath, "");
      vm.detach();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
