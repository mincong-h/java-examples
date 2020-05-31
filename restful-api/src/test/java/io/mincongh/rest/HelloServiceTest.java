package io.mincongh.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloServiceTest {

  private HttpServer server;
  private WebTarget target;

  @BeforeEach
  void setUp() throws Exception {

    server = Main.startServer();
    Client client = ClientBuilder.newClient();

    // uncomment the following line if you want to enable
    // support for JSON in the client (you also have to uncomment
    // dependency on jersey-media-json module in pom.xml and Main.startServer())
    // --
    // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

    target = client.target(Main.BASE_URI);
  }

  @Test
  void testHelloGet() {
    String responseMsg1 = target.path("hello").path("rest").request().get(String.class);
    String responseMsg2 = target.path("hello").path("jersey").request().get(String.class);
    assertEquals("Jersey say : rest", responseMsg1);
    assertEquals("Jersey say : jersey", responseMsg2);
  }

  @AfterEach
  void tearDown() throws Exception {
    server.shutdownNow();
  }
}
