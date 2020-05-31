package io.mincongh.rest;

import static org.assertj.core.api.Assertions.assertThat;

import io.mincongh.rest.dto.User;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests User resource
 *
 * @author Mincong Huang
 */
class UserResourceTest {

  private HttpServer server;

  private UserResource userResource;

  @BeforeEach
  void setUp() {
    server = Main.startServer();
    Configuration config = new ResourceConfig().register(JacksonFeature.class);
    WebTarget target = ClientBuilder.newClient(config).target(Main.BASE_URI);
    userResource = WebResourceFactory.newResource(UserResource.class, target);
  }

  @AfterEach
  void tearDown() {
    server.shutdownNow();
  }

  @Test
  void getUser() {
    User predefined = userResource.getUser(-1);
    assertThat(predefined.getName()).isEqualTo("Foo");
    assertThat(predefined.getAge()).isEqualTo(30);
  }

  @Test
  void createUser() {
    User foo = userResource.createUser(new User("Bar", 18));
    assertThat(foo.getName()).isEqualTo("Bar");
    assertThat(foo.getAge()).isEqualTo(18);
  }

  @Test
  void ping() {
    Response r = userResource.ping();
    assertThat(r.getStatus()).isEqualTo(Status.OK.getStatusCode());
  }
}
