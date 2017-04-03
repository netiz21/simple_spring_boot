package com.thanos.springboot.common.demo.akka;

import akka.actor.AbstractActor;

import java.util.Optional;
import java.util.Random;

/**
 * @author peiheng.zph created on 2017/4/1 23:33
 * @version 1.0
 */
public class AkkaBot extends AbstractActor {
    private static final Random random = new Random();

    private Optional<Direction> direction = Optional.empty();
    private boolean moving = false;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Move.class, this::onMove)
                .match(Stop.class, this::onStop)
                .build();
    }

    private void onMove(Move move) {
        moving = true;
        direction = Optional.of(move.direction);
        System.out.println(getSelf().path() + ": I am now moving " + direction.get());

        // add random fail
        int t = random.nextInt(10);
        if ((t & 1) == 0) {
            getContext().stop(getSelf());
        }
    }

    private void onStop(Stop stop) {
        moving = false;
        System.out.println("I stopped moving");
    }

    public static class Move {
        public final Direction direction;

        public Move(Direction direction) {
            this.direction = direction;
        }
    }

    public static class Stop {
    }

    public static class GetRobotState {
    }

    public static class RobotState {
        public final Direction direction;
        public final boolean moving;

        public RobotState(Direction direction, boolean moving) {
            this.direction = direction;
            this.moving = moving;
        }
    }
}
