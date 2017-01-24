package com.thanos.springboot.agent;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author peiheng.zph created on 17/1/24 上午10:24
 * @version 1.0
 */
public class CustomClassFileTransformer implements ClassFileTransformer, Opcodes {
  private static final Logger logger = LoggerFactory.getLogger(CustomClassFileTransformer.class);

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                          ProtectionDomain protectionDomain, byte[] classfileBuffer)
      throws IllegalClassFormatException {
    logger.info("class file transformer invoked for className: {}", className);

    if (!className.equals("com/thanos/springboot/agent/MyUser")) {
      return null;
    }

    logger.info("Matched class name is {}", className);
    ClassWriter cw = new ClassWriter(0);
    cw.visitEnd();

    // ignore asm work
    return null;
  }
}
