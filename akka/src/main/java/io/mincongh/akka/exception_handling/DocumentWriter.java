package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentWriter extends AbstractActor {

  private static final Logger logger = LoggerFactory.getLogger(DocumentManager.class);

  public static final String WRITE = "write";

  private final DocumentManager.IndicesClient indicesClient;

  private DocumentWriter(DocumentManager.IndicesClient indicesClient) {
    this.indicesClient = indicesClient;
  }

  public static Props props(DocumentManager.IndicesClient indicesClient) {
    return Props.create(DocumentWriter.class, () -> new DocumentWriter(indicesClient));
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(WRITE, this::writeDoc).build();
  }

  @Override
  public void preStart() throws Exception {
    super.preStart();
    logger.info("Starting actor");
    self().tell(WRITE, ActorRef.noSender());
  }

  @Override
  public void postStop() throws Exception {
    logger.info("Actor stopped");
    super.postStop();
  }

  private void writeDoc(String user) {
    logger.info("Writing document for user {}", user);
    var response = indicesClient.create(new CreateIndexRequest("users"), RequestOptions.DEFAULT);
    if (!response.isAcknowledged()) {
      throw new IllegalStateException("Failed to handle create-document request for user " + user);
    }
  }
}
