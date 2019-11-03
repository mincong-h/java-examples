package io.mincongh.elasticsearch;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Index some documents.
 *
 * @author Mincong Huang
 * @since 0.1.0
 * @see <a
 *     href="https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-index.html">Index
 *     some documents</a>
 */
public class IndexIT {

  @Before
  public void setUp() throws Exception {
    // delete existing index if exist
    URL url = new URL("http://localhost:9200/customer/_doc/jdoe?pretty");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("DELETE");
    try {
      conn.connect();
      int statusCode = conn.getResponseCode();
      System.out.println("DELETE " + statusCode + " " + url);
    } finally {
      conn.disconnect();
    }
  }

  @Test
  public void index() throws Exception {
    // HTTP Request
    URL url = new URL("http://localhost:9200/customer/_doc/jdoe?pretty");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("PUT");
    conn.setRequestProperty("Content-Type", "application/json");
    try (OutputStream os = conn.getOutputStream();
        Writer writer = new OutputStreamWriter(os, UTF_8)) {
      // language=json
      writer.write("{ \"name\": \"John Doe\" }");
    }

    // HTTP Response
    try {
      conn.connect();
      int statusCode = conn.getResponseCode();
      assertThat(statusCode).isEqualTo(201);
      Scanner s = new Scanner(conn.getInputStream()).useDelimiter("\\A");
      String content = s.hasNext() ? s.next() : "";
      System.out.println("PUT " + statusCode + " " + url);
      System.out.println(content);
      /*
       * API:
       *   GET  /{index}/{type}/{id}
       *
       * Actual value:
       *   GET  /customer/_doc/jdoe
       *   {
       *     "_index" : "customer",
       *     "_type" : "_doc",
       *     "_id" : "jdoe",
       *     "_version" : 1,
       *     "result" : "created",
       *     "_shards" : {
       *       "total" : 2,
       *       "successful" : 1,
       *       "failed" : 0
       *     },
       *     "_seq_no" : 2,
       *     "_primary_term" : 1
       *   }
       */
      assertThatJson(content)
          .isObject()
          .containsEntry("_index", "customer")
          .containsEntry("_type", "_doc")
          .containsEntry("_id", "jdoe")
          /*
           * The first time a document is uploaded, the version is set to 1.
           * Then, the following modifications will result to version
           * incrementation: 2, 3, 4, ...
           */
          .containsKey("_version")
          .containsEntry("result", "created")
          .containsKey("_seq_no")
          .containsEntry("_primary_term", BigDecimal.valueOf(1));
      assertThatJson(content)
          .node("_shards")
          .isObject()
          .containsEntry("total", BigDecimal.valueOf(2))
          .containsEntry("successful", BigDecimal.valueOf(1))
          .containsEntry("failed", BigDecimal.valueOf(0));
    } finally {
      conn.disconnect();
    }
  }
}
