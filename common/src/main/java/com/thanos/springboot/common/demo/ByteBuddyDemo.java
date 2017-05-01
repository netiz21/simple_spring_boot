package com.thanos.springboot.common.demo;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by solarknight on 2017/5/1.
 */
public class ByteBuddyDemo {

  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    Class<? extends Supplier> supplierType = newSupplierClass();
    Supplier supplier = supplierType.newInstance();
    System.out.println(supplier.get());

    Class<? extends Supplier> supplierType2 = newSupplierClass();
    System.out.println(supplierType.equals(supplierType2));

    Class<? extends Function> funcType = newFunctionClass();
    System.out.println(funcType.newInstance().apply("Lily"));

    funcType = newFunctionClass2();
    Function<String, String> func = funcType.newInstance();
    System.out.println(func.apply("Lucy"));
  }

  private static Class<? extends Supplier> newSupplierClass() {
    return new ByteBuddy()
        .subclass(Supplier.class)
        .method(ElementMatchers.named("get"))
        .intercept(FixedValue.value("Hello, world!"))
        .make()
        .load(ClassLoader.getSystemClassLoader())
        .getLoaded();
  }

  private static Class<? extends Function> newFunctionClass() {
    return new ByteBuddy()
        .subclass(Function.class)
        .method(ElementMatchers.named("apply"))
        .intercept(MethodDelegation.to(new GreetingInterceptor()))
        .make()
        .load(ClassLoader.getSystemClassLoader())
        .getLoaded();
  }

  private static Class<? extends Function> newFunctionClass2() {
    return new ByteBuddy()
        .subclass(Function.class)
        .method(ElementMatchers.named("apply"))
        .intercept(MethodDelegation.to(new GeneralInterceptor()))
        .make()
        .load(ClassLoader.getSystemClassLoader())
        .getLoaded();
  }

  public static class GreetingInterceptor {
    public Object greet(Object argument) {
      return "Hello from " + argument;
    }
  }

  public static class GeneralInterceptor {
    @RuntimeType
    public Object intercept(@AllArguments Object[] allArgs, @Origin Method method) {
      return "Hello from " + allArgs[0];
    }
  }

}
