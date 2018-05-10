package io.mincongh.java9.lang;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests private method in interfaces. Interfaces in the upcoming JVM version can have private
 * methods, which can be used to split lengthy default methods. Syntax:
 *
 * <pre>
 * InterfaceMethodDeclaration:
 *     {InterfaceMethodModifier} MethodHeader MethodBody
 *
 * InterfaceMethodModifier:
 *     (one of)
 *     Annotation public private
 *     abstract default static strictfp
 * </pre>
 *
 * @author Mincong Huang
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se9/html/jls-9.html#jls-9.4">9.4. Method
 *     Declarations</a>
 */
public class InterfacePrivateMethodTest {

  @Test
  public void interfacePrivateMethod() {
    Order order = new Order() {};
    assertEquals("Bla~bla~bla, do this.", order.doThis());
    assertEquals("Bla~bla~bla, do that.", order.doThat());
  }

  private interface Order {
    default String doThis() {
      return prefix() + " do this.";
    }

    default String doThat() {
      return prefix() + " do that.";
    }

    private String prefix() {
      return "Bla~bla~bla,";
    }
  }
}
