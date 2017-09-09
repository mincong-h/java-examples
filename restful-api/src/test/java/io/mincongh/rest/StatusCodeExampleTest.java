package io.mincongh.rest;


import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class StatusCodeExampleTest {

  private HttpServer server;

  private WebTarget codeApi;

  @Before
  public void setUp() throws Exception {
    server = Main.startServer();
    codeApi = ClientBuilder.newClient().target(Main.HTTP_STATUS_CODE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }

  /**
   * HTTP status code 200 - "OK" means the request is okay, entity
   * body contains requested resource.
   */
  @Test
  public void getCode200_ok() throws Exception {
    Response r = codeApi.path("404").request().get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEqualTo("HTTP code: 404");
  }

  /**
   * HTTP status code 400 - "Bad Request" is used to tell the client
   * that it has sent a malformed request.
   */
  @Test
  public void getCode400_badRequest() throws Exception {
    Response r = codeApi.path("sth").request().get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.BAD_REQUEST);
    assertThat(r.readEntity(String.class)).isEqualTo("Invalid code: sth");
  }

  /**
   * HTTP status code 415 - "Unsupported Media Type" is used when a
   * client sends an entity of a content type that the server does not
   * understand or support.
   */
  @Test
  public void getCode415_notAcceptable() throws Exception {
    Response r = codeApi.path("sth").request(MediaType.APPLICATION_JSON).get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.NOT_ACCEPTABLE);
    assertThat(r.readEntity(String.class)).isEmpty();
  }

}
