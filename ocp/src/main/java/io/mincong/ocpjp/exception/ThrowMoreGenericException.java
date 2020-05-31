package io.mincong.ocpjp.exception;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Starting with Java 7, the variable type that you use to rethrow an exception can be more generic
 * in the <code>catch</code> block.
 *
 * @author Mincong Huang
 */
class ThrowMoreGenericException {

  static void throwMe(String source) throws IOException, SQLException {
    try {
      if (source.equals("DBMS")) {
        throw new SQLException();
      } else {
        throw new IOException();
      }
    } catch (Exception e) {
      // Prior to Java 7, the following code fails to compile;

      // With Java 7+, the following code compiles successfully,
      // because the compiler can determine that the type of the
      // checked exception received by the `catch` block would always
      // be either `IOException` or `SQLException`. So it's okay to
      // throw it, even though the type of the variable `e` is
      // `Exception`.
      throw e;
    }
  }
}
