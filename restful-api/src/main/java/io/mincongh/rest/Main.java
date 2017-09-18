package io.mincongh.rest;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class.
 */
public class Main {

  private static final Logger LOGGER = LogManager.getLogger(Main.class);

  /**
   * Base URI the Grizzly HTTP server will listen on
   */
  static final String BASE_URI = "http://localhost:8080/demo";

  /**
   * @see MethodExample
   */
  static final String HTTP_METHOD_URI = BASE_URI + "/method";

  /**
   * @see HeaderExample
   */
  static final String HTTP_HEADER_URI = BASE_URI + "/header";

  /**
   * @see StatusCodeExample
   */
  static final String HTTP_STATUS_CODE_URI = BASE_URI + "/code";

  /**
   * @see BasicAuthExample
   */
  static final String HTTP_BASIC_AUTH_API = BASE_URI + "/auth";

  /**
   * @see JsonExample
   */
  static final String JSON_URI = BASE_URI + "/json";

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application. Firstly,
   * create a resource configuration that scans for JAX-RS resources and providers in
   * {@code io.mincongh.rest} package. Then, create and start a new instance of grizzly http server
   * exposing the Jersey application at {@code BASE_URI}.
   *
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    final ResourceConfig rc = new ResourceConfig().packages("io.mincongh.rest");
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }

  /**
   * Main method.
   */
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    LOGGER.info("Jersey app started with WADL available at {}application.wadl%n"
        + "Hit enter to stop it...", BASE_URI);
    System.in.read();
    server.shutdownNow();
  }
}
