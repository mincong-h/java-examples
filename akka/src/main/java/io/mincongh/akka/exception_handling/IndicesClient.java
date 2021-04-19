package io.mincongh.akka.exception_handling;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

/**
 * Create an interface for indices because the default indices client provided by Elasticsearch is
 * "final", so it cannot be mocked.
 */
interface IndicesClient {
  CreateIndexResponse create(CreateIndexRequest request, RequestOptions options);
}
