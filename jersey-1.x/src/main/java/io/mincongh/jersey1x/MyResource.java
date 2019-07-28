package io.mincongh.jersey1x;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Mincong Huang
 * @since 1.0
 */
@Path("/")
public class MyResource {

  @GET
  @Path("status")
  public Response getStatus() {
    return Response.status(200).build();
  }
}
