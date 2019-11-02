package io.mincongh.akka.got;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class NightKing extends AbstractBehavior<String> {

  private final List<ActorRef<String>> whiteWalkers = new ArrayList<>();

  private NightKing(ActorContext<String> context) {
    super(context);
    getContext().getLog().info("Ready");
  }

  public static Behavior<String> create() {
    return Behaviors.setup(NightKing::new);
  }

  @Override
  public Receive<String> createReceive() {
    return newReceiveBuilder().onMessage(String.class, this::onReceived).build();
  }

  private Behavior<String> onReceived(String command) {
    getContext().getLog().info("{}", command);

    if ("Ah...".equals(command)) {
      getContext().getLog().info("Night King is dead.");
      return Behaviors.stopped();
    }
    if ("White walkers!!!".equals(command)) {
      for (int i = 0; i < 3; i++) {
        ActorRef<String> w = getContext().spawn(WhiteWalker.create(i, 0), "walker-" + i);
        whiteWalkers.add(w);
      }
    }
    if ("Attack!".equals(command)) {
      for (ActorRef<String> w : whiteWalkers) {
        w.tell("N");
      }
    }
    if ("Defend!".equals(command)) {
      for (ActorRef<String> w : whiteWalkers) {
        w.tell("S");
      }
    }
    return this;
  }
}
