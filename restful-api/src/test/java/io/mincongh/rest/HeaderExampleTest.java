package io.mincongh.rest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class HeaderExampleTest {

  private HttpServer server;

  private WebTarget headerApi;

  @Before
  public void setUp() throws Exception {
    server = Main.startServer();
    headerApi = ClientBuilder.newClient().target(Main.HTTP_HEADER_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }

  @Test
  public void doGet_requestHeaderFrom() throws Exception {
    Response r = headerApi.path("From").request().header("From", "abc@example.com").get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEqualTo("From=abc@example.com");
  }

  @Test
  public void doGet_requestHeaderHost() throws Exception {
    Response r = headerApi.path(HttpHeaders.HOST).request().get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEqualTo(HttpHeaders.HOST + "=localhost:8080");
  }

}
