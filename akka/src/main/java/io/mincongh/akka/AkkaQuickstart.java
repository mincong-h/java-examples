package io.mincongh.akka;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import io.mincongh.akka.GreeterMain.Start;
import java.io.IOException;

/**
 * @author Akka Authors
 * @author Mincong Huang
 * @since 0.1.0
 */
public class AkkaQuickstart {
  public static void main(String[] args) {
    /*
     * The guardian actor that bootstraps everything.
     *
     * Every time a message is processed, it is matched against the current
     * behavior of the actor. Behavior means a function which defines the
     * actions ot be taken in reaction to the message at that point in time,
     * say forward a request if the client is authorized, deny it otherwise.
     */
    Behavior<Start> guardianBehavior = GreeterMain.create();
    /*
     * Create actor system.
     *
     * An `ActorSystem` is home to a hierarchy of Actors. It is created using
     * `ActorSystem#apply` from a `Behavior` object that describes the root
     * `Actor` of this hierarchy and which will create all other Actors beneath
     * it. A system also implements the `ActorRef` type, and sending a message
     * to the system directs that message to the root Actor.
     */
    ActorSystem<Start> greeterMain = ActorSystem.create(guardianBehavior, "helloakka");
    /*
     * Sending message via `ActorRef#tell(T msg)`.
     *
     * Actors are represented to the outside using actor references, which are
     * objects that can be passed around freely and without restriction. This
     * split into inner and outer object enables transparency for all the
     * desired operations:
     *
     *   1. Restarting an actor without needing to update references elsewhere
     *   2. Placing the actual actor object on remote hosts
     *   3. Sending messages to actors in completely different applications
     *
     * But the most important aspect is that is it not possible to look inside
     * an actor and get hold of its state from the outside, unless the actor
     * unwisely publishes this information itself.
     */
    greeterMain.tell(new Start("Charles"));

    try {
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ignored) {
    } finally {
      /*
       * Terminate the actor system.
       *
       * This will stop the guardian actor, which in turn will recursively stop
       * all its child actors, and finally the system guardian (below which the
       * logging actors reside).
       *
       * This is an asynchronous operation and completion of the termination
       * can be observed with `ActorSystem.whenTerminated` or
       * `ActorSystem.getWhenTerminated`.
       */
      greeterMain.terminate();
    }
  }
}
