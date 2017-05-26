package com.thanos.springboot.common.demo.polygene;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;

/**
 * @author solarknight created on 17/5/25 下午7:09
 * @version 1.0
 */
public class COPMain {

  public static void main(String[] args) throws Exception {
    SingletonAssembler assembler = new SingletonAssembler() {
      @Override
      public void assemble(ModuleAssembly module) throws AssemblyException {
        module.transients(Speaker.class);
      }
    };
    Speaker speaker = assembler.module().newTransient(Speaker.class);
    System.out.println(speaker.sayHello());
  }
}
