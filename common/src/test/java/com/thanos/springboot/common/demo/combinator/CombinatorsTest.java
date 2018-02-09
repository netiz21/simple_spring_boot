package com.thanos.springboot.common.demo.combinator;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @author peiheng.zph created on 18/2/1 下午7:31
 * @version 1.0
 */
public class CombinatorsTest {

  @Test
  public void combine() throws Exception {
  }

  @Test
  public void combine1() throws Exception {
  }

  @Test
  public void combine2() throws Exception {
  }

  @Test
  public void combine3() throws Exception {
  }

  @Test
  public void combine4() throws Exception {
  }

  @Test
  public void target() throws Exception {
    for (int i = 0; i < 10; i++) {

      Combinator.target(String::length)
          .before(System.out::println)
          .after((args, res) -> System.out.println("Input " + args + " length " + res))
          .supplier(this::randomWord)
          .get();
    }
  }

  private String randomWord() {
    return IntStream.range(1, 25)
        .map(it -> ThreadLocalRandom.current().nextInt(it))
        .mapToObj(it -> String.valueOf((char) ('a' + it)))
        .reduce("", String::concat);
  }

}