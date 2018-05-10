package io.mincongh.java9.lang;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests diamond operator extension for anonymous class. The type inference has been improved, so
 * diamond operator can be used in conjunction with anonymous inner class.
 *
 * @author Mincong Huang
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.9.1">15.9.1.
 *     Determining the Class being Instantiated</a>
 */
public class DiamondOperatorExtensionTest {

  @Test
  public void diamondOperatorExtension() {
    MyClass<String> myStr = new MyClass<>("1");
    MyClass<String> anonymous =
        new MyClass<>("2") {
          @Override
          String getValue() {
            return "2";
          }
        };
    MyClass<?> anonymous2 = new MyClass<>(null);

    assertEquals("1", myStr.getValue());
    assertEquals("2", anonymous.getValue());
    assertNull(anonymous2.getValue());
  }

  private static class MyClass<T> {
    private T t;

    private MyClass(T t) {
      this.t = t;
    }

    T getValue() {
      return t;
    }
  }
}
