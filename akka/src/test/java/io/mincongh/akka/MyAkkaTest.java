package io.mincongh.akka;

import akka.actor.*;
import akka.testkit.javadsl.TestKit;
import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mincong Huang
 * @see <a href="https://doc.akka.io/docs/akka/current/testing.html">Testing Classic Actors</a>
 */
class MyAkkaTest {
  private static ActorSystem system;

  @BeforeAll
  static void beforeClass() {
    system = ActorSystem.create();
  }

  @AfterAll
  static void teardown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  @Test
  void name() {
    /*
     * Wrap the whole test procedure within a testkit constructor
     * if you want to receive actor replies or use Within(), etc.
     */
    new TestKit(system) {
      {
        final Props props = Props.create(SomeActor.class);
        final ActorRef subject = system.actorOf(props);

        // can also use JavaTestKit “from the outside”
        final TestKit probe = new TestKit(system);
        // “inject” the probe by passing it to the test subject
        // like a real resource would be passed in production
        subject.tell(probe.getRef(), getRef());
        // await the correct response
        expectMsg(Duration.ofSeconds(1), "done");

        // the run() method needs to finish within 3 seconds
        within(
            Duration.ofSeconds(3),
            () -> {
              subject.tell("hello", getRef());

              // This is a demo: would normally use expectMsgEquals().
              // Wait time is bounded by 3-second deadline above.
              awaitCond(probe::msgAvailable);

              // response must have been enqueued to us before probe
              expectMsg(Duration.ZERO, "world");
              // check that the probe we injected earlier got the msg
              probe.expectMsg(Duration.ZERO, "hello");
              assertEquals(getRef(), probe.getLastSender());

              // Will wait for the rest of the 3 seconds
              expectNoMessage();
              return null;
            });
      }
    };
  }

  public static class SomeActor extends AbstractActor {
    ActorRef target = null;

    @Override
    public Receive createReceive() {
      return receiveBuilder()
          .matchEquals(
              "hello",
              message -> {
                getSender().tell("world", getSelf());
                if (target != null) {
                  target.forward(message, getContext());
                }
              })
          .match(
              ActorRef.class,
              actorRef -> {
                target = actorRef;
                getSender().tell("done", getSelf());
              })
          .build();
    }
  }
}
