package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentCreator extends AbstractActor {

  private static final Logger logger = LoggerFactory.getLogger(DocumentCreator.class);

  private final ExternalServiceClient externalServiceClient;
  private final CreateDocumentRequest request;

  private DocumentCreator(
      ExternalServiceClient externalServiceClient, CreateDocumentRequest request) {
    this.externalServiceClient = externalServiceClient;
    this.request = request;
  }

  public static Props props(
      ExternalServiceClient externalServiceClient, CreateDocumentRequest request) {
    return Props.create(
        DocumentCreator.class, () -> new DocumentCreator(externalServiceClient, request));
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
    externalServiceClient.createDocument(request);
  }
}
