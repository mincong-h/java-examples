package io.mincongh.java10.lang;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Tests Local-Variable Type Inference.
 *
 * @author Mincong Huang
 * @see <a href="http://openjdk.java.net/jeps/286">JEP 286: Local-Variable Type Inference</a>
 */
class LocalTypeInferenceTest {

  @Test
  void string() {
    var words = new ArrayList<String>();
    words.add("Hello");
    words.add("Java 10");
    words.forEach(System.out::println);
  }
}
