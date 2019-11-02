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
 * @author Akka authors
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
        //#create-actors
        greeter = context.spawn(Greeter.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<Start> createReceive() {
        return newReceiveBuilder().onMessage(Start.class, this::onStart).build();
    }

    private Behavior<Start> onStart(Start command) {
        //#create-actors
        ActorRef<Greeter.Greeted> replyTo =
                getContext().spawn(GreeterBot.create(3), command.name);
        greeter.tell(new Greeter.Greet(command.name, replyTo));
        //#create-actors
        return this;
    }
}
