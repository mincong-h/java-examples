package io.mincongh.akka.exception_handling;

public class CreateDocumentRequest {
  private final String user;

  public CreateDocumentRequest(String user) {
    this.user = user;
  }

  public String user() {
    return user;
  }
}
