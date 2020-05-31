package io.mincongh.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class BasicAuthExampleTest {

  private HttpServer server;

  private WebTarget authApi;

  private Base64.Encoder encoder = Base64.getEncoder();

  @BeforeEach
  void setUp() throws Exception {
    server = Main.startServer();
    authApi = ClientBuilder.newClient().target(Main.HTTP_BASIC_AUTH_URI);
  }

  @AfterEach
  void tearDown() throws Exception {
    server.shutdownNow();
  }

  @Test
  void getUserInfo_authSucceeded() throws Exception {
    byte[] bytes = "foo:foo_password".getBytes(UTF_8);
    String credentials = new String(encoder.encode(bytes), UTF_8);

    Response r = authApi.path("foo").request().header(HttpHeaders.AUTHORIZATION, credentials).get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEqualTo("Authorized (user=foo)");
  }

  @Test
  void getUserInfo_authFailed() throws Exception {
    byte[] bytes = "foo:wrong_password".getBytes(UTF_8);
    String credentials = new String(encoder.encode(bytes), UTF_8);

    Response r = authApi.path("foo").request().header(HttpHeaders.AUTHORIZATION, credentials).get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.UNAUTHORIZED);
    assertThat(r.readEntity(String.class)).isEqualTo("Unauthorized");
  }
}
