package io.mincongh.java8.so41779868;

import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Java Substring in if statement does not work.
 *
 * @author Mincong Huang
 * @see https://stackoverflow.com/questions/41779868
 */
public class SubstringTest {

  @Test
  public void testSubstring() {
    String[] elkon;

    elkon = new String[] {"1", "2", "3"};
    assertEquals("1 2 3", Stream.of(elkon).collect(Collectors.joining(" ")));
    assertEquals("1 2 3", String.join(" ", elkon));

    elkon = new String[] {};
    assertEquals("", Stream.of(elkon).collect(Collectors.joining(" ")));
    assertEquals("", String.join(" ", elkon));
  }
}
