package io.mincongh.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.javadsl.TestKit;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.Optional;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test lifecycle of an Akka actor
 *
 * @author Mincong Huang
 */
public class LifecycleTest {
  private static ActorSystem system;

  @BeforeClass
  public static void beforeClass() {
    system = ActorSystem.create();
  }

  @AfterClass
  public static void teardown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  private static class MyActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(MyActor.class);

    @Override
    public void preStart() throws Exception {
      logger.info("preStart");
      super.preStart();
    }

    @Override
    public void postStop() throws Exception {
      logger.info("postStop");
      super.postStop();
    }

    @Override
    public void aroundPreStart() {
      logger.info("aroundPreStart");
      super.aroundPreStart();
    }

    @Override
    public void aroundPostStop() {
      logger.info("aroundPostStop");
      super.aroundPostStop();
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
      logger.info("aroundPreRestart: {}", reason.getMessage());
      super.aroundPreRestart(reason, message);
    }

    @Override
    public void aroundPostRestart(Throwable reason) {
      logger.info("aroundPostRestart: {}", reason.getMessage());
      super.aroundPostRestart(reason);
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
      logger.info("preRestart: {}", reason.getMessage());
      super.preRestart(reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
      logger.info("postRestart: {}", reason.getMessage());
      super.postRestart(reason);
    }

    @Override
    public Receive createReceive() {
      return receiveBuilder().build();
    }
  }

  private ListAppender<ILoggingEvent> appender;
  private ch.qos.logback.classic.Logger appLogger =
      (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MyActor.class);

  @Before
  public void setUp() {
    appender = new ListAppender<>();
    appender.start();
    appLogger.addAppender(appender);
  }

  @After
  public void tearDown() {
    appLogger.detachAppender(appender);
  }

  @Test
  public void testLifecycle() {
    TestActorRef<MyActor> ref = TestActorRef.create(system, Props.create(MyActor.class));
    ref.suspend();
    ref.restart(new IllegalStateException("something wrong"));
    ref.stop();
    assertThat(appender.list)
        .flatExtracting(ILoggingEvent::getFormattedMessage)
        .containsExactly(
            // start
            "aroundPreStart",
            "preStart",
            // restart
            "aroundPreRestart: something wrong",
            "preRestart: something wrong",
            "postStop",
            "aroundPostRestart: something wrong",
            "postRestart: something wrong",
            "preStart",
            // stop
            "aroundPostStop",
            "postStop"
        );
  }
}
