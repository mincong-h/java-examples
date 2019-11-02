package io.mincongh.akka;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import io.mincongh.akka.Greeter.Greeted;

/**
 * @author Akka Authors
 * @author Mincong Huang
 * @since 0.1.0
 */
public class GreeterBot extends AbstractBehavior<Greeted> {

    public static Behavior<Greeted> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private final int max;
    /*
     * Actor objects will typically contain some variables which reflect
     * possible states the actor may be. This can be an explicit state machine
     * or it could be a counter, set of listeners, pending requests, etc. These
     * data are what make an actor valuable.
     */
    private int greetingCounter;

    private GreeterBot(ActorContext<Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Greeter.Greeted.class, this::onGreeted).build();
    }

    private Behavior<Greeter.Greeted> onGreeted(Greeter.Greeted message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom);
        if (greetingCounter == max) {
            /*
             * Returns this behavior from message processing to signal that
             * this actor shall terminate voluntarily. If this actor has
             * created child actors then these will be stopped as part of the
             * shutdown procedure.
             */
            return Behaviors.stopped();
        } else {
            /*
             * Tell sender to send a new `Greet` message based on the post-
             * greeting message `Greeted`.
             */
            message.from.tell(new Greeter.Greet(message.whom, getContext().getSelf()));
            return this;
        }
    }
}
