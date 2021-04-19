package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;
import java.io.IOException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

public class DocumentWriter extends AbstractActor {

  public static final String WRITE_DOC = "write_doc";
  private final RestHighLevelClient esClient;

  private DocumentWriter(RestHighLevelClient esClient) {
    this.esClient = esClient;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(WRITE_DOC, this::writeDoc).build();
  }

  private void writeDoc(String user) throws IOException {
    var response =
        esClient.indices().create(new CreateIndexRequest("users"), RequestOptions.DEFAULT);
    if (!response.isAcknowledged()) {
      throw new IllegalStateException("Failed to handle create-document request for user " + user);
    }
  }
}
