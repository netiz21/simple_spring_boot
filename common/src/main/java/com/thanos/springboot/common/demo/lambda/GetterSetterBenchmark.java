package com.thanos.springboot.common.demo.lambda;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import io.github.benas.randombeans.api.EnhancedRandom;

/**
 * @author solarknight created on 2017/8/27 16:54
 * @version 1.0
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3)
@Fork(value = 1) // use biased locking
public class GetterSetterBenchmark {

  private static final int COLLECTION_SIZE = 1000;

  @Benchmark
  public void getUseReflect(Blackhole blackhole) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = Person.class.getMethod("getName");
    Person person = EnhancedRandom.random(Person.class);
    blackhole.consume(method.invoke(person));
  }

  @Benchmark
  public void getterUseMethodHandle(Blackhole blackhole) throws Exception {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle getName = lookup.findVirtual(Person.class, "getName", MethodType.methodType(String.class));
    Function<Person, String> nameGetter = LambdaUtils.createGetter(lookup, getName);

    Person person = EnhancedRandom.random(Person.class);
    blackhole.consume(nameGetter.apply(person));
  }

  @Benchmark
  public void multiGetUseReflect(Blackhole blackhole) throws NoSuchMethodException {
    Method method = Person.class.getMethod("getName");

    Stream<Person> stream = EnhancedRandom.randomStreamOf(COLLECTION_SIZE, Person.class);
    stream.map(it -> LambdaUtils.callUnchecked(() -> method.invoke(it)))
        .forEach(blackhole::consume);
  }

  @Benchmark
  public void multiGetUseMethodHandle(Blackhole blackhole) throws Exception {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle getName = lookup.findVirtual(Person.class, "getName", MethodType.methodType(String.class));
    Function<Person, String> nameGetter = LambdaUtils.createGetter(lookup, getName);

    Stream<Person> stream = EnhancedRandom.randomStreamOf(COLLECTION_SIZE, Person.class);
    stream.map(it -> LambdaUtils.callUnchecked(() -> nameGetter.apply(it)))
        .forEach(blackhole::consume);
  }

  @Benchmark
  public void setterUseReflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = Person.class.getMethod("setName", String.class);
    Person person = EnhancedRandom.random(Person.class);
    method.invoke(person, "test");
  }

  @Benchmark
  public void setterUseMethodHandle() throws Exception {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle getName = lookup.findVirtual(Person.class, "setName", MethodType.methodType(void.class, String.class));
    BiConsumer<Person, String> nameSetter = LambdaUtils.createSetter(lookup, getName);

    Person person = EnhancedRandom.random(Person.class);
    nameSetter.accept(person, "test");
  }

  @Benchmark
  public void multiSetterUseReflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method = Person.class.getMethod("setName", String.class);

    Stream<Person> stream = EnhancedRandom.randomStreamOf(COLLECTION_SIZE, Person.class);
    stream.forEach(it -> LambdaUtils.callUnchecked(() -> method.invoke(it, "test")));
  }

  @Benchmark
  public void multiSetterUseMethodHandle() throws Exception {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle getName = lookup.findVirtual(Person.class, "setName", MethodType.methodType(void.class, String.class));
    BiConsumer<Person, String> nameSetter = LambdaUtils.createSetter(lookup, getName);

    Stream<Person> stream = EnhancedRandom.randomStreamOf(COLLECTION_SIZE, Person.class);
    stream.forEach(it -> nameSetter.accept(it, "test"));
  }

  static class Person {
    private String name;
    private int age;
    private int sex;
    private String mobile;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public int getSex() {
      return sex;
    }

    public void setSex(int sex) {
      this.sex = sex;
    }

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }
  }
}
