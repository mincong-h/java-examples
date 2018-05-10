package io.mincong.ocpjp.autoclosing;

/**
 * @author Mincong Huang
 */
public class AutoCloseableObject implements AutoCloseable {

  private StringBuilder sb;

  public AutoCloseableObject(StringBuilder sb) {
    this.sb = sb;
    sb.append("Instantiated; ");
  }

  public void doSthWrong() {
    sb.append("Sth wrong; ");
    throw new RuntimeException("Something wrong.");
  }

  @Override
  public void close() throws Exception {
    sb.append("Closed; ");
  }

}
