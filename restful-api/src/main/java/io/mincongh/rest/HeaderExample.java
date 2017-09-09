package io.mincongh.rest;


import java.util.List;
import java.util.Locale;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Demo class for HTTP headers.
 * <p>
 * Headers and methods work together to determine what clients and
 * servers do. There are headers that are specific for each type of
 * message and headers that are more general in purpose, providing
 * information in both request and response messages. Headers fall
 * into five main classes:
 * <ul>
 * <li>General headers</li>
 * <li>Request headers</li>
 * <li>Response headers</li>
 * <li>Entity headers</li>
 * <li>Extension headers</li>
 * </ul>
 *
 * @author Mincong Huang
 */
@Path("header")
@Produces(MediaType.TEXT_PLAIN)
public class HeaderExample {

  @GET
  @Path("{header}")
  public Response doGet(
      @Context HttpHeaders headers,
      @PathParam("header") String name) {
    List<String> values = headers.getRequestHeader(name);
    String entity = String.format(Locale.ROOT, "%s=%s", name, String.join(",", values));
    return Response.status(Status.OK).entity(entity).build();
  }

}
