package io.mincongh.rest;

import static org.assertj.core.api.Assertions.assertThat;

import io.mincongh.rest.DigestAuthExample.Digest;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class DigestAuthExampleTest {

  private HttpServer server;

  private WebTarget authApi;

  @BeforeEach
  void setUp() {
    server = Main.startServer();
    authApi = ClientBuilder.newClient().target(Main.HTTP_DIGEST_AUTH_URI);
  }

  @AfterEach
  void tearDown() {
    server.shutdownNow();
  }

  @Test
  void getSalesReport_authFailed_noDigest() {
    Response r = authApi.path("sales123").request().get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.UNAUTHORIZED);
  }

  @Test
  void getSalesReport_authFailed_wrongDigest() {
    Response r =
        authApi
            .path("sales123")
            .queryParam("user", "userA")
            .queryParam("digest", "incorrectDigest")
            .request()
            .get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.UNAUTHORIZED);
  }

  @Test
  void getSalesReport_authSucceed_correctDigest() {
    Response r =
        authApi
            .path("sales123")
            .queryParam("user", "userA")
            .queryParam("digest", DigestAuthExample.getDigestAsString("passwordA", Digest.MD5))
            .request()
            .get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
  }
}
