package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import akka.actor.Props;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentManager extends AbstractActor {
  private static final Logger logger = LoggerFactory.getLogger(DocumentManager.class);

  public static final String CREATE_DOC_WITH_BACKOFF = "create_doc_with_backoff";
  public static final String CREATE_DOC_WITHOUT_BACKOFF = "create_doc_without_backoff";

  private final ExternalServiceClient externalServiceClient;
  private final Duration minBackOff;
  private final Duration maxBackOff;
  private final int maxRetries;

  private DocumentManager(
      ExternalServiceClient externalServiceClient,
      Duration minBackOff,
      Duration maxBackOff,
      int maxRetries) {
    this.externalServiceClient = externalServiceClient;
    this.minBackOff = minBackOff;
    this.maxBackOff = maxBackOff;
    this.maxRetries = maxRetries;
  }

  public static Props props(
      ExternalServiceClient externalServiceClient,
      Duration minBackOff,
      Duration maxBackOff,
      int maxRetries) {
    return Props.create(
        DocumentManager.class,
        () -> new DocumentManager(externalServiceClient, minBackOff, maxBackOff, maxRetries));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .matchEquals(CREATE_DOC_WITH_BACKOFF, ignored -> createDocWithBackoff(true))
        .matchEquals(CREATE_DOC_WITHOUT_BACKOFF, ignored -> createDocWithBackoff(false))
        .match(CreateDocumentResponse.class, this::handleResponse)
        .build();
  }

  @Override
  public void postStop() throws Exception {
    logger.error("actor stopped");
    super.postStop();
  }

  private void createDocWithBackoff(boolean withBackoff) {
    var user = "Tom";
    var request = new CreateDocumentRequest(user);

    final Props creatorProps;
    if (withBackoff) {
      logger.info("Creating task for user {} with backoff", user);
      creatorProps =
          DocumentCreator.propsWithBackoff(
              externalServiceClient, self(), request, minBackOff, maxBackOff, maxRetries);
    } else {
      logger.info("Creating task for user {} without backoff", user);
      creatorProps = DocumentCreator.props(externalServiceClient, self(), request);
    }

    context().actorOf(creatorProps);
  }

  private void handleResponse(CreateDocumentResponse response) {
    logger.info("Creation finished: {}", response);
  }
}
