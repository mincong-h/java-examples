package io.mincongh.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Demo class for HTTP status codes.
 *
 * @author Mincong Huang
 */
@Path("code")
@Produces(MediaType.TEXT_PLAIN)
public class StatusCodeExample {

  @GET
  @Path("{code}")
  @Consumes(MediaType.TEXT_PLAIN)
  public Response getCode(@PathParam("code") String code) {
    try {
      int i = Integer.parseInt(code);
      return Response.status(Status.OK).entity("HTTP code: " + i).build();
    } catch (NumberFormatException e) {
      /*
       * Status code:   400
       * Reason phrase: Bad Request
       * Meaning:       Used to tell the client that it has sent a malformed request.
       */
      String msg = "Invalid code: " + code;
      return Response.status(Status.BAD_REQUEST).entity(msg).build();
    }
  }

}
