package io.mincongh.akka.exception_handling;

public class TooManyRequestException extends RuntimeException {
  public TooManyRequestException(String message) {
    super(message);
  }
}
