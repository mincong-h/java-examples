package io.mincongh.akka.exception_handling;

import akka.actor.AbstractActor;

public class DocumentWriter extends AbstractActor {

  public static final String WRITE_DOC = "write_doc";

  @Override
  public Receive createReceive() {
    return receiveBuilder().matchEquals(WRITE_DOC, this::writeDoc).build();
  }

  private void writeDoc(String user) {
    // TODO
  }
}
