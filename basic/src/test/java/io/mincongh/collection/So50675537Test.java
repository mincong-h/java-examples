package io.mincongh.collection;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow:
 * @author Mincong Huang
 */
class So50675537Test {

  private Queue<String> queue;

  @BeforeEach
  void setUp() {
    queue = new LinkedList<>();
    queue.add("A");
    queue.add("B");
    queue.add("C");
  }

  @Test
  void notSolution() {
    StringBuilder sb = new StringBuilder();
    queue.forEach(sb::append);
    assertThat(sb.toString()).isEqualTo("ABC");
    assertThat(queue).isNotEmpty();
  }
}
