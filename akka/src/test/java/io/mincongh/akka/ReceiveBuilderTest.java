package io.mincongh.akka;

import akka.actor.*;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/** @author Mincong Huang */
public class ReceiveBuilderTest {
  private static ActorSystem system;

  @BeforeClass
  public static void beforeClass() throws Exception {
    system = ActorSystem.create();
  }

  @AfterClass
  public static void afterClass() throws Exception {
    TestKit.shutdownActorSystem(system);
  }

  private static class MyActor extends AbstractActor {
    @Override
    public Receive createReceive() {
      return receiveBuilder()
          .match(String.class, s -> getSender().tell("String: " + s, ActorRef.noSender()))
          .match(Integer.class, i -> getSender().tell("Integer: " + i, ActorRef.noSender()))
          .build();
    }
  }

  @Test
  public void testReceiveBuilder() {
    var probe = new TestKit(system);
    var myActor = system.actorOf(Props.create(MyActor.class));

    myActor.tell("Hello", probe.getRef());
    probe.expectMsgEquals("String: Hello");

    myActor.tell(123, probe.getRef());
    probe.expectMsgEquals("Integer: 123");
  }
}
