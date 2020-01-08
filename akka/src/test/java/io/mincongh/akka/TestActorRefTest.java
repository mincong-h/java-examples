package io.mincongh.akka;

import akka.actor.*;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A sample test for {@link TestActorRef}.
 *
 * <p>Unit testing actor logic in a single-threaded environment to ensure single operation works
 * individually. Normally, {@link ActorRef} shields the underlying {@link Actor} instance from the
 * outside and the only way of communication is via the mailbox. However, this is not unit-test
 * friendly.
 *
 * @author Mincong Huang
 * @see <a href="https://doc.akka.io/docs/akka/current/testing.html">Testing Classic Actors</a>
 */
public class TestActorRefTest {

  static class MyActor extends AbstractActor {
    final AtomicInteger value = new AtomicInteger(0);

    @Override
    public Receive createReceive() {
      return receiveBuilder()
          .matchEquals("increment", msg -> value.incrementAndGet())
          .matchEquals("decrement", msg -> value.decrementAndGet())
          .matchEquals("reply", msg -> sender().tell(value.get(), self()))
          .build();
    }
  }

  private ActorSystem system;

  @Before
  public void setUp() {
    system = ActorSystem.create();
  }

  @After
  public void tearDown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  @Test
  public void normalReplyTesting() {
    // Given an actor under test
    Props props = Props.create(MyActor.class);
    TestActorRef<MyActor> myActor = TestActorRef.create(system, props);
    // And a test kit
    TestKit probe = new TestKit(system);

    // When asking for reply
    myActor.tell("reply", probe.getRef());

    // Then the reply is returned
    probe.expectMsgEquals(Duration.ofSeconds(2), 0);
  }

  @Test
  public void increment() {
    // Given an actor under test
    Props props = Props.create(MyActor.class);
    TestActorRef<MyActor> ref = TestActorRef.create(system, props);

    // When sending a "increment" message
    /*
     * Note: messages sent to the actor are process synchronously on
     * the current thread and answers may be sent back as usual.
     */
    ref.tell("increment", ActorRef.noSender());

    // The value is incremented
    assertEquals(1, ref.underlyingActor().value.get());
  }

  @Test
  public void decrement() {
    // Given an actor under test
    Props props = Props.create(MyActor.class);
    TestActorRef<MyActor> ref = TestActorRef.create(system, props);
    /*
     * Note: one common use case is setting up the actor into a
     * specific internal state before sending the test message.
     */
    ref.underlyingActor().value.set(1);

    // When sending a "decrement" message
    ref.tell("decrement", ActorRef.noSender());

    // The value is decremented
    /*
     * Note: another is to verify correct internal state transitions
     * after having sent the test message.
     */
    assertEquals(0, ref.underlyingActor().value.get());
  }
}
