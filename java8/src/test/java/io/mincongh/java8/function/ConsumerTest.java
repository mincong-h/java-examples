package io.mincongh.java8.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** @author Mincong Huang */
class ConsumerTest {

  @Test
  void testNonStaticMethod() {
    StringBuilder builder = new StringBuilder();
    foreach(Arrays.asList("1", "2", "3"), str -> builder.append(str));
    assertEquals("123", builder.toString());
  }

  @Test
  void testStaticMethod() {
    foreach(Arrays.asList("1", "2", "3"), System.out::print);
  }

  private <E> void foreach(Collection<E> collection, Consumer<E> consumer) {
    for (E e : collection) {
      consumer.accept(e);
    }
  }
}
