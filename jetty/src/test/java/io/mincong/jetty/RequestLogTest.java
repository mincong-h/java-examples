package io.mincong.jetty;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class RequestLogTest {

  private Server server;
  private Path logPath;

  @BeforeEach
  void setUp(@TempDir Path tempDir) throws Exception {
    // Create servlet handler
    var servletHolder = new ServletHolder(MyServlet.class);
    var context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(servletHolder, "/");

    // Create log handler
    logPath = tempDir.resolve("request.log").toAbsolutePath();
    var logHandler = new RequestLogHandler();
    logHandler.setRequestLog(new CustomRequestLog(logPath.toString()));
    logHandler.setHandler(context);

    // Start server
    server = new Server(8080);
    server.setHandler(logHandler);
    server.start();
  }

  @AfterEach
  void tearDown() throws Exception {
    server.stop();
  }

  /**
   * Log all HTTP requests into file "request.log", such as:
   *
   * <pre>
   * 127.0.0.1 - - [04/Jul/2020:08:38:59 +0000] "GET / HTTP/1.1" 200 31 "-" "Java-http-client/11.0.2"
   * </pre>
   */
  @Test
  void requestLog() throws Exception {
    // When sending a HTTP request
    var httpClient = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder(URI.create("http://localhost:8080")).build();
    var response = httpClient.send(request, BodyHandlers.ofString());

    // Then the response is OK
    assertThat(response.statusCode()).isEqualTo(200);

    // And the request is logged
    assertThat(Files.readString(logPath))
        .startsWith("127.0.0.1 - - [")
        .contains("] \"GET / HTTP/1.1\" 200 31 \"-\" \"Java-http-client/");
  }

  public static class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
      response.getWriter().write("{\"message\":\"Welcome to Jetty!\"}");
    }
  }
}
