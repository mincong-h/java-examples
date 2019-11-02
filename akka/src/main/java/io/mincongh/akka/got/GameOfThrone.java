package io.mincongh.akka.got;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import java.io.IOException;

/**
 * @author Mincong Huang
 * @since 0.1.0
 */
public class GameOfThrone {
  public static void main(String[] args) {
    Behavior<String> guardianBehavior = Army.create();
    ActorSystem<String> army = ActorSystem.create(guardianBehavior, "army");

    try {
      System.out.println(">>> Press ENTER to exit <<<");
      army.tell("Winter");
      army.tell("Long Night");
      System.in.read();
    } catch (IOException ignored) {
    } finally {
      army.terminate();
    }
  }
}
