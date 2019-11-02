package io.mincongh.akka.got;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * The Army of the Dead.
 *
 * @author Mincong Huang
 * @since 0.1.0
 */
public class Army extends AbstractBehavior<String> {

  private final ActorRef<String> nightKing;
  private Army(ActorContext<String> context) {
    super(context);
    nightKing = getContext().spawn(NightKing.create(), "king");
  }

  public static Behavior<String> create() {
    return Behaviors.setup(Army::new);
  }

  @Override
  public Receive<String> createReceive() {
    return newReceiveBuilder().onMessage(String.class, this::onStart).build();
  }

  private Behavior<String> onStart(String moment) {
    if ("Winter".equals(moment)) {
      getContext().getLog().info("Winter is coming");
      nightKing.tell("White walkers!!!");
    }
    if ("Long Night".equals(moment)) {
      getContext().getLog().info("Long Night");
      nightKing.tell("Attack!");
      nightKing.tell("Attack!");
      nightKing.tell("Ah..."); // night king is killed
    }
    return this;
  }
}
