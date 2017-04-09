package com.thanos.springboot.common.demo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Test for biased locking {@see https://srvaroa.github.io/jvm/java/openjdk/biased-locking/2017/01/30/hashCode.html}
 * <p>
 * Created by solarknight on 2017/4/9.
 */

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 4)
@Fork(value = 5, jvmArgsAppend = {"-XX:BiasedLockingStartupDelay=0"}) // use biased locking
//@Fork(value = 5, jvmArgsAppend = {"-XX:-UseBiasedLocking", "-XX:BiasedLockingStartupDelay=0"})
public class BiasedLockingBenchmark {

  int unsafeCounter = 0;
  Object withIdHash;
  Object withoutIdHash;

  @Setup
  public void setup() {
    withIdHash = new Object();
    withoutIdHash = new Object() {
      @Override
      public int hashCode() {
        return 1;
      }
    };
    withIdHash.hashCode();
    withoutIdHash.hashCode();
  }

  @Benchmark
  public void withIdHash(Blackhole bh) {
    synchronized (withIdHash) {
      bh.consume(unsafeCounter++);
    }
  }

  @Benchmark
  public void withoutIdHash(Blackhole bh) {
    synchronized (withoutIdHash) {
      bh.consume(unsafeCounter++);
    }
  }

  @Benchmark
  @Threads(2)
  public void withoutIdHashContended(Blackhole bh) {
    synchronized (withoutIdHash) {
      bh.consume(unsafeCounter++);
    }
  }

  @Benchmark
  @Threads(2)
  public void withIdHashContended(Blackhole bh) {
    synchronized (withoutIdHash) {
      bh.consume(unsafeCounter++);
    }
  }

}
