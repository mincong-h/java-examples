package io.mincongh.java9.lang;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests try-with-resources statement. Since Java 9, the variable declaration before each managed
 * resource is no longer required. The old syntax:
 *
 * <pre>
 * try ({VariableModifier} R Identifier = Expression ...)
 *     Block
 * </pre>
 *
 * can be now simplified to:
 *
 * <pre>
 * try (VariableAccess ...)
 *     Block
 * </pre>
 *
 * @author Mincong Huang
 * @see <a
 *     href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20.3.1">14.20.3.1.
 *     Basic try-with-resources</a>
 */
public class TryWithResourcesStatementTest {

  @Test
  public void autoCloseableSyntax() {
    Foo foo = new Foo();
    try (foo) {
      assertFalse(foo.isClosed);
    }
    assertTrue(foo.isClosed);
  }

  private class Foo implements AutoCloseable {

    private boolean isClosed = false;

    @Override
    public void close() {
      isClosed = true;
    }
  }
}
