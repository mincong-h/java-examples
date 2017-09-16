package io.mincongh.rest;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Rather than passively trying to guess the identity of a user from
 * his IP address, a web server can explicitly ask the user who he is
 * by requiring him to authenticate (log in) with a username and
 * password.
 * <p>
 * The help make web site login easier, HTTP includes a built-in
 * mechanism to pass username information to web sites, using the
 * "WWW-Authenticate" and "Authorization" headers. Once logged in,
 * the browsers continually send this login information with each
 * request to the site, so the information is always available.
 * <p>
 * If a server wants a user to register before providing access to
 * the site, it can send back an HTTP 401 Login Required response
 * code to the browser. The browser will then display a login dialog
 * box and supply the information in the next request to the browser,
 * using the "Authorization" header.
 *
 * @author Mincong Huang
 */
@Path("auth")
@Produces(MediaType.TEXT_PLAIN)
public class BasicAuthExample {

  /**
   * User credentials map, where key is username, and value is the
   * base-64 encoded value.
   */
  private static final Map<String, byte[]> CREDENTIALS;

  private static final Base64.Encoder ENCODER = Base64.getEncoder();

  static {
    Map<String, byte[]> map = new HashMap<>();
    map.put("foo", encode("foo", "foo_password"));
    map.put("bar", encode("bar", "bar_password"));
    CREDENTIALS = Collections.unmodifiableMap(map);
  }

  @GET
  @Path("{user}")
  public Response getUserInfo(
      @HeaderParam(HttpHeaders.AUTHORIZATION) String credentials,
      @PathParam("user") String user) {
    if (isCorrect(user, credentials)) {
      return Response.status(Status.OK).entity("Authorized (user=" + user + ")").build();
    } else {
      /*
       * The client tried to operate on a protected resource without
       * providing the proper authentication credentials. It may have
       * provided the wrong credentials, or none at all.
       */
      return Response.status(Status.UNAUTHORIZED).entity("Unauthorized").build();
    }
  }

  private static byte[] encode(String username, String password) {
    String token = username + ":" + password;
    byte[] bytes = token.getBytes(UTF_8);
    return ENCODER.encode(bytes);
  }

  private boolean isCorrect(String user, String credential) {
    if (credential == null || !CREDENTIALS.containsKey(user)) {
      return false;
    }
    byte[] expected = CREDENTIALS.get(user);
    byte[] actual = credential.getBytes(UTF_8);
    return Arrays.equals(expected, actual);
  }

}
