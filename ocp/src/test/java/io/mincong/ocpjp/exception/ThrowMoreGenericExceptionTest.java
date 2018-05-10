package io.mincong.ocpjp.exception;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class ThrowMoreGenericExceptionTest {

  @Test(expected = SQLException.class)
  public void throwMe_SQLException() throws Exception {
    ThrowMoreGenericException.throwMe("DBMS");
  }

  @Test(expected = IOException.class)
  public void throwMe_IOException() throws Exception {
    ThrowMoreGenericException.throwMe("Other");
  }

}
