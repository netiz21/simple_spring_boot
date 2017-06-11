package com.thanos.springboot.common.demo.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

/**
 * @author peiheng.zph created on 2017/4/2 13:46
 * @version 1.0
 */
public class BotMaster extends AbstractActor {
  private static final int DEFAULT_CHILD_SIZE = 10;

  public BotMaster() {
    for (int index = 0; index < DEFAULT_CHILD_SIZE; index++) {
      ActorRef child = getContext().actorOf(Props.create(AkkaBot.class));
      getContext().watch(child);
    }
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(StartChildBots.class, this::onStartChildBots)
        .match(Terminated.class, this::onChildTerminated)
        .build();
  }

  private void onStartChildBots(StartChildBots startChildBots) {
    final AkkaBot.Move move = new AkkaBot.Move(Direction.FORWARD);
    for (ActorRef child : getContext().getChildren()) {
      System.out.println("Master started moving " + child);
      child.tell(move, getSelf());
    }
  }

  private void onChildTerminated(Terminated terminated) {
    System.out.println("Child has stopped, starting a new one");
    ActorRef child = getContext().actorOf(Props.create(AkkaBot.class));
    getContext().watch(child);
  }

  public static class StartChildBots {
  }
}
