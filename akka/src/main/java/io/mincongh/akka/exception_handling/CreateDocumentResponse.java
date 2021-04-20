package io.mincongh.akka.exception_handling;

public class CreateDocumentResponse {

  private final boolean isSuccessful;

  private CreateDocumentResponse(boolean isSuccessful) {
    this.isSuccessful = isSuccessful;
  }

  public boolean isSuccessful() {
    return isSuccessful;
  }

  public boolean isFailed() {
    return !isSuccessful;
  }
}
