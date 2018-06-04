package io.mincongh.collection;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow:
 * @author Mincong Huang
 */
public class So50675537Test {

  private Queue<String> queue;

  @Before
  public void setUp() {
    queue = new LinkedList<>();
    queue.add("A");
    queue.add("B");
    queue.add("C");
  }

  @Test
  public void notSolution() {
    StringBuilder sb = new StringBuilder();
    queue.forEach(sb::append);
    assertThat(sb.toString()).isEqualTo("ABC");
    assertThat(queue).isNotEmpty();
  }
}
