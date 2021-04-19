package io.mincongh.akka.exception_handling;

import static akka.actor.SupervisorStrategy.*;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.BackoffOpts;
import akka.pattern.BackoffSupervisor;
import java.io.IOException;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentManager extends AbstractActor {
  private static final Logger logger = LoggerFactory.getLogger(DocumentManager.class);
  public static final String WRITE_DOC = "write_doc";

  private final ExternalServiceClient externalServiceClient;
  private final Duration minBackOff;
  private final Duration maxBackOff;

  private DocumentManager(
      ExternalServiceClient externalServiceClient, Duration minBackOff, Duration maxBackOff) {
    this.externalServiceClient = externalServiceClient;
    this.minBackOff = minBackOff;
    this.maxBackOff = maxBackOff;
  }

  public static Props props(
      ExternalServiceClient externalServiceClient, Duration minBackOff, Duration maxBackOff) {
    return Props.create(
        DocumentManager.class,
        () -> new DocumentManager(externalServiceClient, minBackOff, maxBackOff));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(WRITE_DOC, this::createDoc).build();
  }

  @Override
  public void postStop() throws Exception {
    logger.error("actor stopped");
    super.postStop();
  }

  private void createDoc(String user) throws IOException {
    logger.info("Creating task for user {}", user);

    // min=1s, max=16s
    // 1s
    // 2s (±10%)
    // 4s (±10%)
    // 8s (±10%)
    // 16s (±10%)
    var childProps = DocumentCreator.props(externalServiceClient, "Tom");

    context()
        .actorOf(
            BackoffSupervisor.props(
                BackoffOpts.onFailure(childProps, "document-creator", minBackOff, maxBackOff, 0.1)
                    .withSupervisorStrategy(
                        new OneForOneStrategy(
                                DeciderBuilder.match(TooManyRequestsException.class, e -> restart())
                                    .matchAny(o -> stop())
                                    .build())
                            .withMaxNrOfRetries(5))));
  }
}
