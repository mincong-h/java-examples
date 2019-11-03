package io.mincongh.elasticsearch;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use the cat health API to verify that your three-node cluster is up running. The cat APIs return
 * information about your cluster and indices in a format that’s easier to read than raw JSON.
 *
 * <p>You can interact directly with your cluster by submitting HTTP requests to the Elasticsearch
 * REST API. Most of the examples in this guide enable you to copy the appropriate cURL command and
 * submit the request to your local Elasticsearch instance from the command line. If you have Kibana
 * installed and running, you can also open Kibana and submit requests through the Dev Console.
 *
 * @see <a
 *     href="https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-install.html">Get
 *     Elasitcsearch up and running</a>
 */
public class HealthIT {

  @Test
  public void checkHealth() throws Exception {
    /*
     * Difference between port 9200 and 9300:
     *
     * - 9200 is for REST
     * - 9300 is for node communication
     *
     * See https://discuss.elastic.co/t/elasticsearch-port-9200-or-9300/72080
     */
    URL url = new URL("http://localhost:9200/_cat/health?v");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    try {
      assertThat(conn.getResponseCode()).isEqualTo(200);
      Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
      String content = s.hasNext() ? s.next() : "";
      System.out.println(content);
    } finally {
      conn.disconnect();
    }
  }
}
