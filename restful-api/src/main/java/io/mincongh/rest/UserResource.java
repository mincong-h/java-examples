package io.mincongh.rest;

import io.mincongh.rest.dto.User;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User resource.
 *
 * @author Mincong Huang
 */
@Path("users")
public interface UserResource {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  User createUser(User user);

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  User getUser(@PathParam("id") int id);

  @HEAD
  Response ping();
}
