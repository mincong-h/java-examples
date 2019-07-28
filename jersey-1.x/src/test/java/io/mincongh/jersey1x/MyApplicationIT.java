package io.mincongh.jersey1x;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import java.net.URI;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class MyApplicationIT {

  private URI uri;
  private HttpServer server;

  @Before
  public void setUp() throws Exception {
    uri = UriBuilder.fromUri("http://localhost/api/").port(8080).build();
    server = GrizzlyServerFactory.createHttpServer(uri, new MyApplication());
  }

  @After
  public void tearDown() {
    server.stop();
  }

  @Test
  public void status() {
    WebResource webResource = Client.create().resource(uri);
    ClientResponse response = webResource.path("status").get(ClientResponse.class);
    assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
  }
}
