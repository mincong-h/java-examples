package io.mincongh.akka.exception_handling;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.stop;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.japi.pf.DeciderBuilder;
import akka.pattern.BackoffOpts;
import akka.pattern.BackoffSupervisor;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentCreator extends AbstractActor {

  private static final Logger logger = LoggerFactory.getLogger(DocumentCreator.class);

  private final ExternalServiceClient externalServiceClient;
  private final CreateDocumentRequest request;
  private final ActorRef managerRef;

  private DocumentCreator(
      ExternalServiceClient externalServiceClient,
      ActorRef managerRef,
      CreateDocumentRequest request) {
    this.externalServiceClient = externalServiceClient;
    this.managerRef = managerRef;
    this.request = request;
  }

  public static Props props(
      ExternalServiceClient externalServiceClient,
      ActorRef managerRef,
      CreateDocumentRequest request) {
    return Props.create(
        DocumentCreator.class,
        () -> new DocumentCreator(externalServiceClient, managerRef, request));
  }

  public static Props propsWithBackoff(
      ExternalServiceClient externalServiceClient,
      ActorRef managerRef,
      CreateDocumentRequest request,
      Duration minBackOff,
      Duration maxBackOff,
      int maxRetries) {
    var childProps = DocumentCreator.props(externalServiceClient, managerRef, request);

    // min=1s, max=16s
    //
    // 1s
    // 2s (+ ≤10%)
    // 4s (+ ≤10%)
    // 8s (+ ≤10%)
    // 16s (+ ≤10%)
    //
    return BackoffSupervisor.props(
        BackoffOpts.onFailure(childProps, "document-creator", minBackOff, maxBackOff, 0.1)
            .withSupervisorStrategy(
                new OneForOneStrategy(
                        DeciderBuilder.match(TooManyRequestsException.class, e -> restart())
                            .matchAny(o -> stop())
                            .build())
                    .withMaxNrOfRetries(maxRetries)));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(CreateDocumentRequest.class, this::createDoc).build();
  }

  @Override
  public void preStart() throws Exception {
    super.preStart();
    logger.info("Starting actor");
    self().tell(request, ActorRef.noSender());
  }

  @Override
  public void postStop() throws Exception {
    logger.info("Actor stopped");
    super.postStop();
  }

  private void createDoc(CreateDocumentRequest request) {
    logger.info("Creating document for user {}", request.user());
    var response = externalServiceClient.createDocument(request);

    // only submit successful response, failure will be retried
    managerRef.tell(response, self());
  }
}
