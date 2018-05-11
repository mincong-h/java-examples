package io.mincongh.rest;

/** @author Mincong Huang */
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
