package io.mincongh.rest;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class JsonExampleTest {

  private HttpServer server;

  private WebTarget methodApi;

  @BeforeEach
  void setUp() throws Exception {
    server = Main.startServer();
    methodApi = ClientBuilder.newClient().target(Main.JSON_URI);
  }

  @AfterEach
  void tearDown() throws Exception {
    server.shutdownNow();
  }

  @Test
  void doPost() throws Exception {
    Entity<String> entity = Entity.json("{\"name\":\"Tom\"}");
    Response r = methodApi.request().post(entity);
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    assertThat(r.readEntity(String.class)).isEqualTo("{\"message\":\"Hello\"}");
  }
}
