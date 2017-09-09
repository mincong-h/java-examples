package io.mincongh.rest;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Demo class for HTTP methods.
 * <p>
 * HTTP defines a set of methods are called <i>safe</i> methods. The
 * <tt>GET</tt> and <tt>HEAD</tt> methods are said to be safe,
 * meaning that no action should occur as a result of an HTTP request
 * that uses either the <tt>GET</tt> or <tt>HEAD</tt> method.
 *
 * @author Mincong Huang
 */
@Path("method")
@Produces(MediaType.TEXT_PLAIN)
public class MethodExample {

  /**
   * GET is the most common method. It usually is used to ask a
   * server to send a resource. HTTP/1.1 requires servers to
   * implement this method.
   */
  @GET
  public Response doGet() {
    return Response.status(Status.OK).entity("HTTP method: GET").build();
  }

  /**
   * HEAD behaves exactly like the GET method, but the server returns
   * only the headers in the response. No entity body is ever
   * returned. This allows a client to inspect the headers for a
   * resource without having to actually get the resource. Using
   * HEAD, you can
   * <ul>
   * <li>Find out about a resource (e.g., determine its type) without
   * getting it.</li>
   * <li>See if an object exists, by looking at the status code of
   * the response.</li>
   * <li>Test if the resource has been modified, by looking at the
   * headers.</li>
   * </ul>
   * <p>
   * Server developers must ensure that the headers returned are
   * exactly those that a GET request would return. The HEAD method
   * also is required for HTTP/1.1 compliance.
   */
  @HEAD
  public Response doHead() {
    return Response.status(Status.OK).build();
  }

  /**
   * PUT writes documents to a server, in the inverse of the way that
   * GET reads documents from a server. Some publishing systems let
   * you create web pages and install them directly on a web server
   * using PUT.
   * <p>
   * The semantics of the PUT method are for the server to take the
   * body of the request and either use it to create a new document
   * named by the requested URL or, if that URL already exists, use
   * the body to replace it.
   * <p>
   * Because PUT allows you to change content, many web servers
   * require you to log in with a password before you can perform a
   * PUT.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public Response doPut(String xml) {
    String entity = "HTTP method: PUT\n";
    entity += "XML resource uploaded, see " + Main.BASE_URI + "/fake.xml";
    return Response.status(Status.CREATED).entity(entity).build();
  }

  /**
   * POST was designed to send input data to the server. In practice,
   * it is often used to support HTML forms. The data from a
   * filled-in typically is sent to the server,  which then marshals
   * it off to where it needs to go (e.g., to a server gateway
   * program, which then processes it).
   */
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response doPost(
      @FormParam("name") String name,
      @FormParam("age") int age) {
    String entity = "HTTP method: POST\n";
    entity += "name: " + name + "\n";
    entity += "age: " + age;
    return Response.status(Status.OK).entity(entity).build();
  }

}
