package io.mincongh.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Demo class for JSON usages.
 *
 * @author Mincong Huang
 */
@Path("json")
@Produces(MediaType.APPLICATION_JSON)
public class JsonExample {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response doPost(String json) {
    return Response.status(Status.OK).entity("{\"message\":\"Hello\"}").build();
  }

}
