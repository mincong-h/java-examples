package io.mincongh.akka;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import io.mincongh.akka.Greeter.Greet;
import io.mincongh.akka.Greeter.Greeted;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Akka Authors
 * @author Mincong Huang
 * @since 0.1.0
 */
public class AkkaQuickstartTest {

  @ClassRule public static final TestKitJunitResource testKit = new TestKitJunitResource();

  @Test
  public void testGreeterActorSendingOfGreeting() {
    TestProbe<Greeted> testProbe = testKit.createTestProbe();
    ActorRef<Greet> underTest = testKit.spawn(Greeter.create(), "greeter");
    underTest.tell(new Greeter.Greet("Charles", testProbe.getRef()));
    testProbe.expectMessage(new Greeter.Greeted("Charles", underTest));
  }
}
