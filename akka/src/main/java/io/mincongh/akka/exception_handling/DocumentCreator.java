package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentCreator extends AbstractActor {

  private static final Logger logger = LoggerFactory.getLogger(DocumentCreator.class);

  public static final String CREATE = "write";

  private final ExternalServiceClient externalServiceClient;
  private final String user;

  private DocumentCreator(ExternalServiceClient externalServiceClient, String user) {
    this.externalServiceClient = externalServiceClient;
    this.user = user;
  }

  public static Props props(ExternalServiceClient externalServiceClient, String user) {
    return Props.create(
        DocumentCreator.class, () -> new DocumentCreator(externalServiceClient, user));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(CREATE, ignored -> createDoc()).build();
  }

  @Override
  public void preStart() throws Exception {
    super.preStart();
    logger.info("Starting actor");
    self().tell(CREATE, ActorRef.noSender());
  }

  @Override
  public void postStop() throws Exception {
    logger.info("Actor stopped");
    super.postStop();
  }

  private void createDoc() {
    logger.info("Creating document for user {}", user);
    externalServiceClient.createDocument(user);
  }
}
