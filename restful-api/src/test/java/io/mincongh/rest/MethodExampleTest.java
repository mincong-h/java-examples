package io.mincongh.rest;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 */
class MethodExampleTest {

  private HttpServer server;

  private WebTarget methodApi;

  @BeforeEach
  void setUp() throws Exception {
    server = Main.startServer();
    methodApi = ClientBuilder.newClient().target(Main.HTTP_METHOD_URI);
  }

  @AfterEach
  void tearDown() throws Exception {
    server.shutdownNow();
  }

  @Test
  void doGet() throws Exception {
    Response r = methodApi.request().get();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEqualTo("HTTP method: GET");
  }

  @Test
  void doHead() throws Exception {
    Response r = methodApi.request().head();
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).isEmpty();
  }

  @Test
  void doPut() throws Exception {
    Entity<String> entity = Entity.xml("<user>Jersey</user>");
    Response r = methodApi.request().put(entity);
    assertThat(r.getStatusInfo()).isEqualTo(Status.CREATED);
    assertThat(r.readEntity(String.class)).contains("HTTP method: PUT", "XML resource uploaded");
  }

  @Test
  void doPost() throws Exception {
    Form form = new Form();
    form.param("name", "foo");
    form.param("age", "18");
    Response r = methodApi.request().post(Entity.form(form));
    assertThat(r.getStatusInfo()).isEqualTo(Status.OK);
    assertThat(r.readEntity(String.class)).contains("HTTP method: POST", "name: foo", "age: 18");
  }

}
