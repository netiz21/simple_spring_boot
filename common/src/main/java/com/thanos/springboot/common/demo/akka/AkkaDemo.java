package com.thanos.springboot.common.demo.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

/**
 * @author peiheng.zph created on 2017/3/31 22:32
 * @version 1.0
 */
public class AkkaDemo {

  public static void main(String[] args) throws IOException {
    ActorSystem actorSystem = ActorSystem.create();

//        ActorRef akkaBot = actorSystem.actorOf(Props.create(AkkaBot.class), "akkaBot");
//        akkaBot.tell(new AkkaBot.Move(Direction.FORWARD), ActorRef.noSender());
//        akkaBot.tell(new AkkaBot.Move(Direction.BACKWARDS), ActorRef.noSender());
//        akkaBot.tell(new AkkaBot.Stop(), ActorRef.noSender());

    ActorRef botMaster = actorSystem.actorOf(Props.create(BotMaster.class), "botMaster");
    botMaster.tell(new BotMaster.StartChildBots(), ActorRef.noSender());
  }
}
