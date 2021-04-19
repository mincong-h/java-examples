package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.BackoffOpts;
import akka.pattern.BackoffSupervisor;
import java.io.IOException;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentManager extends AbstractActor {
  private static final Logger logger = LoggerFactory.getLogger(DocumentManager.class);
  public static final String WRITE_DOC = "write_doc";

  private final IndicesClient indicesClient;
  private final Duration minBackOff;
  private final Duration maxBackOff;

  private DocumentManager(IndicesClient indicesClient, Duration minBackOff, Duration maxBackOff) {
    this.indicesClient = indicesClient;
    this.minBackOff = minBackOff;
    this.maxBackOff = maxBackOff;
  }

  public static Props props(IndicesClient indicesClient, Duration minBackOff, Duration maxBackOff) {
    return Props.create(
        DocumentManager.class, () -> new DocumentManager(indicesClient, minBackOff, maxBackOff));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(WRITE_DOC, this::writeDoc).build();
  }

  @Override
  public void postStop() throws Exception {
    logger.error("actor stopped");
    super.postStop();
  }

  private void writeDoc(String user) throws IOException {
    logger.info("Creating task for user {}", user);

    // min=1s, max=16s
    // 1s
    // 2s (+100%)
    // 4s (+100%)
    // 8s (+100%)
    // 16s (+100%)
    var childProps = DocumentWriter.props(indicesClient);
    context()
        .actorOf(
            BackoffSupervisor.props(
                BackoffOpts.onFailure(childProps, "document-writer", minBackOff, maxBackOff, 1.0)
                    .withMaxNrOfRetries(5)));
  }
}
