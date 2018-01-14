package com.thanos.springboot.common.demo.lambda;

import com.thanos.springboot.common.demo.combinator.Before;

import java.util.function.Consumer;

/**
 * @author solarknight created on 2018/1/7 17:32
 * @version 1.0
 */
public class DefaultMethodDemo {

  public static void main(String[] args) {
    ElectricPlayer player1 = it -> ("Playing " + it + " now");

    ElectricPlayer player2 = new ElectricPlayer() {
      @Override
      public String apply(String disc) {
        return "Playing " + disc + " now";
      }

      @Override
      public void start() {
        System.out.println("Start playing...");
      }

      @Override
      public void stop() {
        System.out.println("Stop playing...");
      }
    };

    player1 = decorate(player1);
    player2 = decorate(player2);

    System.out.println(player1.apply("Le Rouge et le Noir"));
    System.out.println(player2.apply("Faust"));
  }

  public static ElectricPlayer decorate(ElectricPlayer player) {
    Consumer<String> consumer = it -> System.out.println("Input is " + it);
    return Before.decorate(consumer, player)::apply;
  }
}
