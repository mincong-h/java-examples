package io.mincong.ocpjp.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mincong Huang
 */
public class BlockingQueueTest {

  private BlockingQueue<String> queue;

  private BlockingQueue<String> fullQueue;

  private BlockingQueue<String> emptyQueue;

  private static final int CAPACITY = 10;

  @Before
  public void setUp() throws Exception {
    emptyQueue = new ArrayBlockingQueue<>(CAPACITY);

    queue = new ArrayBlockingQueue<>(CAPACITY);
    queue.add("A");

    fullQueue = new ArrayBlockingQueue<>(CAPACITY);
    for (int i = 0; i < CAPACITY; i++) {
      fullQueue.add(String.valueOf(i));
    }
  }

  @Test
  public void add_whenQueueIsNotFull() throws Exception {
    queue.add("B");
    assertThat(queue).contains("A", "B");
    assertThat(queue.remainingCapacity()).isEqualTo(CAPACITY - 2);
  }

  @Test(expected = IllegalStateException.class)
  public void add_whenQueueFull() throws Exception {
    fullQueue.add("A");
  }

  @Test
  public void offer_whenQueueIsNotFull() throws Exception {
    boolean isInserted = queue.offer("A");
    assertThat(isInserted).isTrue();
  }

  @Test
  public void offer_whenQueueFull() throws Exception {
    boolean isInserted = fullQueue.offer("A");
    assertThat(isInserted).isFalse();
  }

  @Test
  public void remove() throws Exception {
    queue.remove("A");
    assertThat(queue).isEmpty();
  }

  @Test
  public void removeIf() throws Exception {
    queue.add("B1");
    queue.add("B2");
    queue.removeIf(s -> s.startsWith("B"));
    assertThat(queue).contains("A");
  }

  @Test
  public void drainTo() throws Exception {
    List<String> list = new ArrayList<>();
    queue.drainTo(list);
    assertThat(list).contains("A");
    assertThat(queue).isEmpty();
  }

  @Test
  public void drainToMax() throws Exception {
    queue.add("B");
    List<String> list = new ArrayList<>();
    queue.drainTo(list, 1);
    assertThat(list).contains("A");
    assertThat(queue).contains("B");
  }

  @Test
  public void take() throws Exception {
    assertThat(queue.take()).isEqualTo("A");
  }

  @Test
  public void poll_withoutTimeout() throws Exception {
    assertThat(queue.poll()).isEqualTo("A");
  }

  @Test
  public void poll_withTimeout() throws Exception {
    assertThat(queue.poll(1, TimeUnit.SECONDS)).isEqualTo("A");
  }

  @Test
  public void poll_whenQueueIsEmpty() throws Exception {
    assertThat(emptyQueue).isEmpty();
    assertThat(emptyQueue.poll()).isNull();
  }

}
