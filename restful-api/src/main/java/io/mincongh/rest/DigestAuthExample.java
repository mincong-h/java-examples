package io.mincongh.rest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Basic authentication is convenient and flexible but completely
 * <b>insecure</b>. User-names and passwords are sent in the clear,
 * and there is no attempt to protect messages from tempering. The
 * only way to use basic authentication securely is to use it it
 * conjunction with SSL.
 * <p>
 * Digest authentication was developed as a compatible, more secure
 * alternative to basic authentication, We devote this chapter to the
 * theory and practice of digest authentication.
 * <p>
 * Digest authentication is an alternate HTTP authentication protocol
 * that tries to fix the most serious flows of basic authentication.
 * In particular, digest authentications:
 * <ul>
 * <li>Never sends secret passwords across the network in the clear
 * <li>Prevents unscrupulous individuals from capturing and
 * replaying authentication handshakes
 * <li>Optionally can guard against tampering with message contents
 * <li>Guards against several other common forms of attacks
 * </ul>
 * <p>
 * Digest authentication is not the most secure protocol possible.
 * Many needs for secure HTTP transactions cannot be met by digest
 * authentications. For those needs, Transport Layer Security (TLS)
 * and Secure HTTP (HTTPS) are more appropriate protocols.
 * <p>
 * The motto of digest authentication is <i>"Never send the password
 * across the network."</i>. Instead of sending the password, the
 * client sends a "fingerprint" of "digest" of the password, which is
 * an irreversible scrambling of the password. The client and the
 * server both know the secret password, so the server can verify
 * that the digest provided a correct match for the password. Given
 * only the digest, a bad guy has no easy way to find what password
 * it came from, other than going through every password in the
 * universe, trying each one. (There are techniques, such as
 * dictionary a attacks, where common passwords are tried first.)
 *
 * @author Mincong Huang
 */
@Path("digest-auth")
@Produces(MediaType.TEXT_PLAIN)
public class DigestAuthExample {

  /**
   * A digest is a "condensation of a body of information." Digests
   * acts as onw-way functions, typically converting an infinite
   * number of possible input values into a finite range of
   * condensations.
   */
  enum Digest {
    /**
     * MD5 stands for "MessageDigest #5", one in a series of digest
     * algorithms. It converts any arbitrary sequence of bytes, of
     * any length into a 128-bit digest.
     */
    MD5,
    /**
     * The Secure Hash Algorithm (SHA) is another popular digest
     * function.
     */
    SHA
  }

  private static final MessageDigest MESSAGE_DIGEST;

  static {
    try {
      MESSAGE_DIGEST = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Map<String, String> CREDENTIALS_MAP;

  static {
    Map<String, String> map = new HashMap<>();
    map.put("userA", "passwordA");
    map.put("userB", "passwordB");
    CREDENTIALS_MAP = Collections.unmodifiableMap(map);
  }

  @GET
  @Path("{id}")
  public Response getSalesReport(
      @HeaderParam(HttpHeaders.AUTHORIZATION) String credentials,
      @PathParam("id") String docId,
      @QueryParam("user") String user,
      @QueryParam("digest") String digest) {
    if (isDigestCorrect(user, digest)) {
      return Response.status(Status.OK).entity("Authorized.").build();
    } else {
      /*
       * The server refuses to serve the document until the client
       * authenticates its identity by proving it knows the password.
       * The server issues a challenge to the client, asking for the
       * username and a digested from of the password.
       */
      String msg = "You requested a secret financial document."
          + " Please tell me your username and password digest.";
      return Response.status(Status.UNAUTHORIZED).entity(msg).build();
    }
  }

  static String getDigestAsString(String password, Digest digest) {
    switch (digest) {
      case MD5:
        byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
        return new String(MESSAGE_DIGEST.digest(bytes), StandardCharsets.UTF_8);
      case SHA:
        // TODO
        return "";
      default:
        throw new IllegalStateException("Unknown digest type: " + digest);
    }
  }

  private static boolean isDigestCorrect(String user, String actualDigest) {
    if (actualDigest == null) {
      return false;
    }
    if (!CREDENTIALS_MAP.containsKey(user)) {
      return false;
    }
    String expectedPassword = CREDENTIALS_MAP.get(user);
    String expectedDigest = getDigestAsString(expectedPassword, Digest.MD5);
    return actualDigest.equals(expectedDigest);
  }

}
