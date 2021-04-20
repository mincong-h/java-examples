package io.mincongh.akka.exception_handling;

interface ExternalServiceClient {
  /**
   * Create a new document.
   *
   * @param request the creation request
   * @return the creation response if the operation succeeds
   * @throws RuntimeException when any error occurred
   */
  CreateDocumentResponse createDocument(CreateDocumentRequest request);
}
