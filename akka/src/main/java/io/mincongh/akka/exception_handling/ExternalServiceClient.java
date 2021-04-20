package io.mincongh.akka.exception_handling;

interface ExternalServiceClient {
  String createDocument(CreateDocumentRequest request);
}
