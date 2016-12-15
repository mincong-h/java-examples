package io.mincongh.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "hello" path)
 */
@Path("/hello")
public class HelloService {

  @GET
  @Path("/{param}")
  public Response getMessage(@PathParam("param") String msg) {
    String output = "Jersey say : " + msg;
    return Response.status(200).entity(output).build();
  }
}
