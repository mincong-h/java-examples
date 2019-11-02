package io.mincongh.akka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import io.mincongh.akka.Greeter.Greet;
import io.mincongh.akka.GreeterMain.Start;

/**
 * @author Akka Authors
 * @author Mincong Huang
 * @since 0.1.0
 */
public class GreeterMain extends AbstractBehavior<Start> {

  public static class Start {
    public final String name;

    public Start(String name) {
      this.name = name;
    }
  }

  private final ActorRef<Greet> greeter;

  public static Behavior<Start> create() {
    return Behaviors.setup(GreeterMain::new);
  }

  private GreeterMain(ActorContext<Start> context) {
    super(context);
    /*
     * Create actor: Greeter
     *
     *     akka://helloakka/user/greeter
     */
    greeter = context.spawn(Greeter.create(), "greeter");
  }

  @Override
  public Receive<Start> createReceive() {
    return newReceiveBuilder()
        /*
         * Create a "receive" behavior which executes the `onStart()` method
         * when a `Start` message is received.
         */
        .onMessage(Start.class, this::onStart)
        .build();
  }

  private Behavior<Start> onStart(Start startCmd) {
    /*
     * Create actor: GreeterBot
     *
     * Naming convention of reference:
     *
     *     akka://{actorSystem}/user/{behavior}
     *
     * Here, the actor system is called "helloakka" and the start command is
     * called "Charles", so the actor reference is:
     *
     *     akka://helloakka/user/Charles
     */
    ActorRef<Greeter.Greeted> bot = getContext()
        /*
         * Each actor is potentially a supervisor: if it creates children for
         * delegating sub-tasks, it will automatically supervise them. The list
         * of children is maintained within the actor's context and the actor
         * has access to it. Modifications to the list are done by creating
         * (`context.actorOf(...)`) or stopping (`context.stop(child)`)
         * children and these actions are reflected immediately. The actual
         * creation and termination actions happened behind the scenes in an
         * asynchronous way, so they do not block their supervisor.
         */
        .spawn(GreeterBot.create(3), startCmd.name);
    greeter.tell(new Greeter.Greet(startCmd.name, bot));
    return this;
  }
}
