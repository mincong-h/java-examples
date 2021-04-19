package io.mincongh.akka.got;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class WhiteWalker extends AbstractBehavior<String> {

  // position (x, y) of the walker
  private int x;
  private int y;

  private WhiteWalker(ActorContext<String> context, int x, int y) {
    super(context);
    this.x = x;
    this.y = y;
    getContext().getLog().info("Ready ({}, {})", x, y);
  }

  public static Behavior<String> create(int x, int y) {
    return Behaviors.setup(ctx -> new WhiteWalker(ctx, x, y));
  }

  @Override
  public Receive<String> createReceive() {
    return newReceiveBuilder().onMessage(String.class, this::onReceived).build();
  }

  private Behavior<String> onReceived(String command) {
    int x0 = x;
    int y0 = y;
    for (char direction : command.toCharArray()) {
      switch (direction) {
        case 'N':
          y++;
          break;
        case 'E':
          x++;
          break;
        case 'S':
          y--;
          break;
        case 'W':
          x--;
          break;
        default:
          getContext().getLog().warn("Unknown direction: {}", direction);
          break;
      }
    }
    getContext().getLog().info("({}, {}) -> ({}, {})", x0, y0, x, y);
    return this;
  }
}
